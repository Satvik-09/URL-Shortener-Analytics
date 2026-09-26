package com.satvik.url_shortner.exception;

public class UrlNotFoundException extends RuntimeException {
    private String message;

    public UrlNotFoundException(String message){
        super(message);
    }

}
