package com.example.QLSTK.controller;

import com.example.QLSTK.dto.*;
import com.example.QLSTK.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signIn(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.signIn(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new LoginResponse(null, null, e.getMessage()));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@RequestBody RegisterRequest request) {
        try {
            UserResponse response = authService.signUp(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UserResponse(null, null, null, null, null, e.getMessage()));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            authService.sendPasscode(request.getEmail());
            return ResponseEntity.ok(new MessageResponse("Passcode đã được gửi đến email."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/verify-passcode")
    public ResponseEntity<MessageResponse> verifyPasscode(@RequestBody VerifyPasscodeRequest request) {
        try {
            authService.verifyPasscode(request.getEmail(), request.getPasscode());
            return ResponseEntity.ok(new MessageResponse("Xác minh passcode thành công."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            authService.resetPassword(request.getEmail(), request.getNewPassword(), request.getConfirmPassword());
            return ResponseEntity.ok(new MessageResponse("Đặt lại mật khẩu thành công."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(e.getMessage()));
        }
    }
}