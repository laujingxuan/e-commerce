package com.example.user.response;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ApiResponse {
    private HttpStatus status;
    private String message;
    private Object data;
    private String error;
    private long timestamp = new Date().getTime();
    private int statusCode;

    public ApiResponse() {
    }

    public ApiResponse(HttpStatus status, String message, Object data, String error, int statusCode) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.error = error;
        this.statusCode = statusCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
