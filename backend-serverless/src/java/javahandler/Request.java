package javahandler;

public class Request {
    private String id;

    public Request() {
    }

    public Request(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}