package edu.escuelaing.arep.controller;

import edu.escuelaing.arep.annotations.GetMapping;
import edu.escuelaing.arep.annotations.RequestParam;
import edu.escuelaing.arep.annotations.RestController;

@RestController
public class ServerController {

    @GetMapping("/greeting")
    public static String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hola " + name;
    }

    @GetMapping("/pi")
    public static String pi(@RequestParam(value = "name", defaultValue = "Samuel") String name) {
        return Double.toString(Math.PI);
    }
}