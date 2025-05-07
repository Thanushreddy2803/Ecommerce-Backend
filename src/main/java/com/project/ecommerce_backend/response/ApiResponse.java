package com.project.ecommerce_backend.response;

public class ApiResponse {
    private String Message;
    private boolean Status;

    public ApiResponse() {
    }

    public ApiResponse(String message, boolean status) {
        super();
        Message = message;
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }



}
