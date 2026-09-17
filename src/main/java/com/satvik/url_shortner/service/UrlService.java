package com.satvik.url_shortner.service;

import com.satvik.url_shortner.entity.UrlMapping;
import com.satvik.url_shortner.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UrlService {

     @Autowired
    private UrlRepository urlrepository;
     public String shortenurl(String originalUrl){

         UrlMapping mapping = new UrlMapping();
         mapping.setOriginalUrl(originalUrl);
         mapping.setCreatedAt(LocalDateTime.now());

         String Tempcode = UUID.randomUUID().toString().substring(0,6);
         mapping.setShortcode(Tempcode);

         urlrepository.save(mapping);
         return Tempcode;
     }

     public String getOriginalurl(String shortcode){
         UrlMapping mapping = urlrepository.findByShortcode(shortcode).orElseThrow(() -> new RuntimeException("URL not found"));
         return mapping.getOriginalUrl();
     }
}
