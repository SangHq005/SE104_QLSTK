package com.example.QLSTK.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "SOTIETKIEM")
@Data
public class SoTietKiem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maSTK;

    private String tenSTK;
    private float soTienGuiToiThieu;
    private int kyHan;
    private float laiSuat;
    private String tenKH;
    private String diaChi;
}