package ca.vanier.vctranswebbe.controller;

public class Response {
    private String status;
    private Object result;
    
    //Getters
    public String getStatus() {
        return status;
    }
    public Object getResult() {
        return result;
    }
    
    //Setters
    public void setStatus(String status) {
        this.status = status;
    }
    public void setResult(Object result) {
        this.result = result;
    }
}
