package com.example.QLSTK.controller;

import com.example.QLSTK.dto.LoginRequest;
import com.example.QLSTK.dto.LoginResponse;
import com.example.QLSTK.dto.NguoiDungDTO;
import com.example.QLSTK.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public void register(@RequestBody NguoiDungDTO dto) {
        authService.register(dto);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}