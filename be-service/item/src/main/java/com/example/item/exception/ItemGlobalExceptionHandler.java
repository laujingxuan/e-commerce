package com.example.item.exception;

import com.example.shared.response.SharedErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ItemGlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<SharedErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
        SharedErrorResponse err = new SharedErrorResponse("Illegal arguments: " + e.getMessage(), 402);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<SharedErrorResponse> handleIllegalAccess(IllegalAccessException e) {
        SharedErrorResponse err = new SharedErrorResponse("Illegal access: " + e.getMessage(), 401);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<SharedErrorResponse> handleNotFoundException(CustomNotFoundException e) {
        SharedErrorResponse err = new SharedErrorResponse(e.getMessage(), 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SharedErrorResponse> handleAllOtherException(Exception e) {
        SharedErrorResponse err = new SharedErrorResponse(e.getMessage(), 405);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
}
