package edu.escuelaing.arep.controller;

import edu.escuelaing.arep.WebApplication;
import edu.escuelaing.arep.annotations.GetMapping;
import edu.escuelaing.arep.annotations.RequestParam;
import edu.escuelaing.arep.annotations.RestController;

@RestController
public class ServerController {

    @GetMapping("/hello")
    public static String hello() {
        return "Hello World!";
    }

    @GetMapping("/greeting")
    public static String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello " + name;
    }

//    @GetMapping("/static")
//    public static void changeFolder(@RequestParam(value = "folder", defaultValue = "12:00 AM") String folder) {
//        WebApplication.changeFolder(folder);
//    }

    @GetMapping("/pi")
    public static String pi() {
        return Double.toString(Math.PI);
    }

    @GetMapping("/e")
    public static String e() {
        return Double.toString(Math.E);
    }
}