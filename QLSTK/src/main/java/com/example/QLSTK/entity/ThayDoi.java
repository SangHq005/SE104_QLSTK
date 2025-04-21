package com.example.QLSTK.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "THAYDOI")
public class ThayDoi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maTD;

    @ManyToOne
    @JoinColumn(name = "MaND", nullable = false)
    private NguoiDung nguoiDung;

    @ManyToOne
    @JoinColumn(name = "MaSTK", nullable = false)
    private SoTietKiem soTietKiem;

    private Float soTienGuiToiThieu;
    private Integer kyHan;
    private Float laiSuat;
}