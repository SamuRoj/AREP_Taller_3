package edu.escuelaing.arep.http;

public class HttpRequest {
    private String path;
    private String query;

    public HttpRequest(String path, String query) {
        this.path = path;
        this.query = query;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getValues(String value){
        String[] params = this.query.split("&");
        for(String i : params){
            if(i.split("=")[0].equals(value)) return i.split("=")[1];
        }
        return "Not Found.";
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "path='" + path + '\'' +
                ", query='" + query + '\'' +
                '}';
    }
}