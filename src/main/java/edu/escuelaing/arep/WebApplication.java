package edu.escuelaing.arep;

import edu.escuelaing.arep.http.HttpServer;

import java.io.IOException;
import java.net.URISyntaxException;

import static edu.escuelaing.arep.http.HttpServer.get;
import static edu.escuelaing.arep.http.HttpServer.staticFiles;

public class WebApplication {

    public static void main(String[] args) throws IOException, URISyntaxException {
        staticFiles("/newFolder");

        get("/hello", (req, res) -> "Hello World!");

        get("/greeting", (req, res) -> {
            return "Hello " + req.getValues("name");
        });

        get("/pi", (req, res) -> {
            return String.valueOf(Math.PI);
        });

        get("/e", (req, res) -> {
            return String.valueOf(Math.E);});

        HttpServer.start(args);
    }

    static void changeDirectory(String path){
        staticFiles(path);
    }
}
