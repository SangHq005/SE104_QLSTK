package com.example.QLSTK.dto;

import lombok.Data;

@Data
public class VerifyPasscodeRequest {
    private String email;
    private String passcode;
}