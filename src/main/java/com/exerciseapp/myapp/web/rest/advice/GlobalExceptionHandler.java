package com.exerciseapp.myapp.web.rest.advice;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> resolveException(BadCredentialsException exception) {
        Map<String, String> body = new HashMap<>();
        body.put("code", HttpStatus.BAD_REQUEST.value() + "");
        body.put("message", "Invalid account");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
