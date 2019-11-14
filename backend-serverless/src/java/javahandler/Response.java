package javahandler;

public class Response {
    private String status;
    private String message;
    private String info1;
    private String info2;
    private String open;

    public Response() {
    }

    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public Response(String status, String info1, String info2, String open) {
        this.status = status;
        this.info1 = info1;
        this.info2 = info2;
        this.open = open;
    }

    public String getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public String getInfo1() {
        return this.info1;
    }

    public String getInfo2() {
        return this.info2;
    }

    public String getOpen() {
        return this.open;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public void setOpen(String open) {
        this.open = open;
    }
}