package edu.escuelaing.arep.controller;

import edu.escuelaing.arep.annotations.DeleteMapping;
import edu.escuelaing.arep.annotations.GetMapping;
import edu.escuelaing.arep.annotations.PostMapping;
import edu.escuelaing.arep.annotations.RequestParam;

public class ApiController {
    @GetMapping("/activities")
    public static String getActivities() {
        return "Hello World!";
    }

    @PostMapping("/post/activities")
    public static String addActivity(@RequestParam(value = "time", defaultValue = "12:00 AM") String time,
                                     @RequestParam(value = "name", defaultValue = "Sleep") String name) {
        return "Hello " + name;
    }

    @DeleteMapping("/delete/activities")
    public static String deleteActivity(@RequestParam(value = "time", defaultValue = "12:00 AM") String time,
                                     @RequestParam(value = "name", defaultValue = "Sleep") String name) {
        return "Hello " + name;
    }
}
