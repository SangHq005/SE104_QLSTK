package com.example.QLSTK.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "DANGNHAP")
public class DangNhap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maDN;

    @ManyToOne
    @JoinColumn(name = "MaND", nullable = false)
    private NguoiDung nguoiDung;

    private String matKhau;

    @Temporal(TemporalType.TIMESTAMP)
    private Date loginTime;
}