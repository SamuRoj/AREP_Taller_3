package edu.escuelaing.arep;

import edu.escuelaing.arep.http.HttpServer;

import java.io.IOException;
import java.net.URISyntaxException;

import static edu.escuelaing.arep.http.HttpServer.get;
import static edu.escuelaing.arep.http.HttpServer.staticFiles;

public class WebApplication {

    public static void main(String[] args) throws IOException, URISyntaxException {
        loadComponents();
        HttpServer.start(args);
    }

    /**
     * Load everything from the controller package from the disk
     * The get method should add to the map a Method object and invoke it. Now the lambdas doesn't apply?
     * I have to use the get method of httpServer
     */
    private static void loadComponents(){

    }

    public static void changeFolder(String folder){
        HttpServer.staticFiles(folder);
    }
}
