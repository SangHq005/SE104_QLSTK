package com.example.QLSTK.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String email;
    private int vaiTro;
}