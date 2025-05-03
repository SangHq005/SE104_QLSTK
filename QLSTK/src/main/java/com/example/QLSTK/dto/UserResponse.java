package com.example.QLSTK.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Integer maND;
    private String tenND;
    private String email;
    private String sdt;
    private Integer vaiTro;
    private String message;

    public UserResponse() {
    }

    public UserResponse(Integer maND, String tenND, String email, String sdt, Integer vaiTro) {
        this.maND = maND;
        this.tenND = tenND;
        this.email = email;
        this.sdt = sdt;
        this.vaiTro = vaiTro;
    }

    public UserResponse(Integer maND, String tenND, String email, String sdt, Integer vaiTro, String message) {
        this.maND = maND;
        this.tenND = tenND;
        this.email = email;
        this.sdt = sdt;
        this.vaiTro = vaiTro;
        this.message = message;
    }
}