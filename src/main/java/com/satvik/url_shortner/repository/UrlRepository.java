package com.satvik.url_shortner.repository;

import com.satvik.url_shortner.entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlMapping,Long> {
    Optional<UrlMapping> findByShortcode(String shortcode);//findByShortcode written in camelCase where one of the word is capital
}
