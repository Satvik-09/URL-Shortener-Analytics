package com.satvik.url_shortner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateUrlRequest {
    @NotBlank(message = "URL cannot be Empty")
    @Pattern(regexp = "^(https?://).+",message = "URL must start with http:// or https://")
    private String url;

   public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
