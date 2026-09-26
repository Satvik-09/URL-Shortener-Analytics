package com.satvik.url_shortner.dto;

import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {

    @NotBlank(message = "username cannot be Empty")
    private String username;

    @NotBlank(message = "password cannot be Empty")
    private String password;

    public   String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public   String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
