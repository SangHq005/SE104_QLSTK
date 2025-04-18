package com.example.QLSTK.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "DANGNHAP")
@Data
public class DangNhap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maDN;

    @ManyToOne
    @JoinColumn(name = "maND")
    private NguoiDung nguoiDung;

    private String matKhau;

}