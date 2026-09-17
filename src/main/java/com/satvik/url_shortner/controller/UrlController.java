package com.satvik.url_shortner.controller;


import com.satvik.url_shortner.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
public class UrlController {

    @Autowired
    private UrlService urlservice;

    @PostMapping("/shorten")
    public ResponseEntity<String> shorten(@RequestBody Map<String,String> request){
        String shortcode = urlservice.shortenurl(request.get("url"));
        return ResponseEntity.ok(shortcode);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code){
        String originalUrl = urlservice.getOriginalurl(code);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
    }
}
