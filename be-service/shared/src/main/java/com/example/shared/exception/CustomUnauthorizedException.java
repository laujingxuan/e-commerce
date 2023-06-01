package com.example.shared.exception;

public class CustomUnauthorizedException extends RuntimeException{
    public CustomUnauthorizedException(String message) {
        super(message);
    }

    public CustomUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
