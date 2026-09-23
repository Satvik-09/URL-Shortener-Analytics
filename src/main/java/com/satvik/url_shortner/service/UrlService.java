package com.satvik.url_shortner.service;

import com.satvik.url_shortner.entity.UrlMapping;
import com.satvik.url_shortner.repository.UrlRepository;
import com.satvik.url_shortner.util.Base62;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UrlService {


    private  final UrlRepository urlrepository;

     public String shortenurl(String originalUrl){

         UrlMapping mapping = new UrlMapping();
         mapping.setOriginalUrl(originalUrl);
         mapping.setCreatedAt(LocalDateTime.now());

         UrlMapping saved = urlrepository.save(mapping);//it saved check mapping.getid() and newly creatd mapping so its null and saves url,creatdat in db
         String shortcode = Base62.encode(saved.getId());//methodcall to base62 class and encodes it
         saved.setShortcode(shortcode);//it performs setter method call and intializes shortcode to id
         urlrepository.save(saved);//saves shortcode = id

         return shortcode;
     }

     public String getOriginalurl(String shortcode){
         //checks whether optional[urlrepo] object has some id to return else throws exception
         UrlMapping mapping = urlrepository.findByShortcode(shortcode).orElseThrow(() -> new RuntimeException("No URL Found at:" + shortcode));
         return mapping.getOriginalUrl();
     }
}
