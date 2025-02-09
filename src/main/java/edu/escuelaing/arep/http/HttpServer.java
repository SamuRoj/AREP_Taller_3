package edu.escuelaing.arep.http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class HttpServer {

    private static List<Activity> activities = new ArrayList<>();
    private static final int PORT = 23727;
    private static String route = "target/classes";
    private static Map<String, BiFunction<HttpRequest, HttpResponse, String>> services= new HashMap();

    public static void start(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        boolean isRunning = true;
        System.out.println("Server started through port " + PORT);
        while(isRunning){
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine = "";
            boolean isFirstLine = true;
            String file = "";
            String method = "";

            while((inputLine = in.readLine()) != null){
                if(isFirstLine){
                    method = inputLine.split(" ")[0];
                    file = inputLine.split(" ")[1];
                    isFirstLine = false;
                }
                if (!in.ready()) break;
            }

            URI resourceURI = new URI(file);
            HttpRequest req = new HttpRequest(resourceURI.getPath(), resourceURI.getQuery());
            HttpResponse res = new HttpResponse();
            String outputLine = processRequest(method, req, res, clientSocket.getOutputStream());
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    static String processRequest(String method, HttpRequest req, HttpResponse res, OutputStream out) throws IOException {
        switch (method) {
            case "GET":
                if (req.getPath().startsWith("/app/activity")) return obtainActivities();
                if (req.getPath().startsWith("/app")) return answerRequest(req, res);
                else return obtainFile(req.getPath(), out);
            case "POST": {
                String time = req.getQuery().split("&")[0].split("=")[1];
                String activity = req.getQuery().split("&")[1].split("=")[1];
                activities.add(new Activity(time, activity));
                return "HTTP/1.1 201 Accepted\r\n"
                        + "Content-Type: text/plain\r\n"
                        + "\r\n";
            }
            case "DELETE": {
                String time = req.getQuery().split("=")[1];
                Predicate<Activity> condition = activity -> activity.getTime().equals(time);
                activities.removeIf(condition);
                return "HTTP/1.1 201 Accepted\r\n"
                        + "Content-Type: text/plain\r\n"
                        + "\r\n";
            }
            default:
                return "HTTP/1.1 405 Method Now Allowed\r\n"
                        + "Content-Type: text/plain\r\n"
                        + "\r\n";
        }
    }

    public static String obtainFile(String path, OutputStream out) throws IOException {
        String file = path.equals("/") ? "index.html" : path.split("/")[1];
        String extension = file.split("\\.")[1];
        String header = obtainContentType(extension);
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: " + header + "\r\n" +
                "\r\n";
        String notFound = "HTTP/1.1 404 Not Found\r\n"
                + "Content-Type: text/plain\r\n"
                + "\r\n";
        String inputLine;
        StringBuilder fileContent = new StringBuilder();
        if(extension.equals("html") || extension.equals("css") || extension.equals("js")){
            try {
                BufferedReader in = new BufferedReader(new FileReader((route +  "/" +  file)));
                while((inputLine = in.readLine()) != null){
                    fileContent.append(inputLine).append("\n");
                    if (!in.ready()) break;
                }
                in.close();
                return response + fileContent;
            } catch (FileNotFoundException e) {
                return notFound;
            }
        }
        else if(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")){
            obtainImage(file, response, out);
            return response;
        }
        else return notFound;
    }

    static String answerRequest(HttpRequest req, HttpResponse res){
        BiFunction<HttpRequest, HttpResponse, String> service = services.get(req.getPath());
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: application/json\r\n"
                + "\r\n"
                + "{\"response\":\""+ service.apply(req, res) +"\"}";
    }

    static void obtainImage(String file, String response, OutputStream out) throws IOException {
        File imageFile = new File(route + "/" + file);
        if(imageFile.exists()){
            FileInputStream fis = new FileInputStream(imageFile);
            byte[] imageBytes = new byte[(int) imageFile.length()];
            fis.read(imageBytes);
            out.write(response.getBytes());
            out.write(imageBytes);
        }
        out.write(("HTTP/1.1 404 Not Found\r\n"
                + "Content-Type: text/plain\r\n"
                + "\r\n").getBytes());
    }

    static String obtainActivities(){
        StringBuilder json = new StringBuilder();
        json.append("[");

        boolean first = true;
        for (Activity a : activities) {
            if (!first) {
                json.append(",");
            }
            first = false;
            json.append("{")
                    .append("\"time\": \"").append(a.getTime()).append("\", ")
                    .append("\"activity\": \"").append(a.getName()).append("\"")
                    .append("}");
        }

        json.append("]");
        return"HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                json;
    }

    public static void get(String route, BiFunction<HttpRequest, HttpResponse, String> function){
        services.put("/app" + route, function);
    }

    public static void staticFiles(String path){
        route = "target/classes" + path;
    }

    public static String obtainContentType(String extension){
        if(extension.equals("html") || extension.equals("css")) return "text/" + extension;
        else if(extension.equals("js")) return "text/javascript";
        else if(extension.equals("jpg") || extension.equals("jpeg")) return "image/jpeg";
        else if(extension.equals("png")) return "image/png";
        return "text/plain";
    }
}