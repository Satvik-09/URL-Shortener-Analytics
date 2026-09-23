package com.satvik.url_shortner.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionalHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidexception(MethodArgumentNotValidException ex){
        Map<String,String> Errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->Errors.put(error.getField(),error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(Errors);
    }
}
