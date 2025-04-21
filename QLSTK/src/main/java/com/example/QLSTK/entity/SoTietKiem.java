package com.example.QLSTK.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "SOTIETKIEM")
public class SoTietKiem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maSTK;

    private String tenSTK;
    private Float soTienGuiToiThieu;
    private Integer kyHan;
    private Float laiSuat;
    private String tenKH;
    private String diaChi;

    @OneToMany(mappedBy = "soTietKiem", cascade = CascadeType.ALL)
    private List<MoSoTietKiem> moSoTietKiemList;

    @OneToMany(mappedBy = "soTietKiem", cascade = CascadeType.ALL)
    private List<ThayDoi> thayDoiList;
}