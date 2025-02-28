package com.bookclub.dto;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class LoginForm {

    @Valid
    private String username;

    @Valid
    private String password;

    // Getters and setters...
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
