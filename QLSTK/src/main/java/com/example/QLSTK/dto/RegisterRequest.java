package com.example.QLSTK.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String phoneNumber;
    private String password;
    private String confirmPassword;
}