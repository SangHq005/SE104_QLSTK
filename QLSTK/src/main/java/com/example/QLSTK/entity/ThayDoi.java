package com.example.QLSTK.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "THAYDOI")
@Data
public class ThayDoi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maTD;

    @ManyToOne
    @JoinColumn(name = "maND")
    private NguoiDung nguoiDung;

    @ManyToOne
    @JoinColumn(name = "maSTK")
    private SoTietKiem soTietKiem;

    private float soTienGuiToiThieu;
    private int kyHan;
    private float laiSuat;
}