package com.example.user.exception;

import com.example.shared.exception.CustomUnauthorizedException;
import com.example.shared.response.SharedErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class UserGlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(UserGlobalExceptionHandler.class);

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<SharedErrorResponse> handleDataViolationException(DataIntegrityViolationException e) {
        String errMsg = e.getMessage();
        if (e.getMessage().contains("email")) {
            errMsg = "Email constraints not met";
        } else if (e.getMessage().contains("username")) {
            errMsg = "Username constraints not met";
        }
        SharedErrorResponse err = new SharedErrorResponse(errMsg, 411);
        logger.warn("Save user: {}", errMsg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<SharedErrorResponse> handleAuthenticationException(AuthenticationException e) {
        // if authentication failed
        // When you use .build(), it indicates that the response entity does not have a body.
        // If you want to include a response body, you can use .body(...) to set the response body and return the ResponseEntity directly without calling .build()
        SharedErrorResponse err = new SharedErrorResponse("Unauthorized", 401);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(CustomUnauthorizedException.class)
    public ResponseEntity<SharedErrorResponse> handleIllegalAccess(CustomUnauthorizedException e) {
        SharedErrorResponse err = new SharedErrorResponse("Illegal access: " + e.getMessage(), 401);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SharedErrorResponse> handleAllOtherException(Exception e) {
        SharedErrorResponse err = new SharedErrorResponse(e.getMessage(), 405);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
}
