package edu.escuelaing.arep.http;

public class Activity {
    private String time;
    private String name;

    public Activity(String time, String name){
        this.time = time;
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "time='" + time + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}