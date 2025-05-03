package com.example.QLSTK.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private UserResponse user;
    private String token;
}