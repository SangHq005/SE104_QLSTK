package com.example.QLSTK.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "MOSOTIETKIEM")
public class MoSoTietKiem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maMSTK;

    @ManyToOne
    @JoinColumn(name = "MaKH", nullable = false)
    private NguoiDung nguoiDung;

    @ManyToOne
    @JoinColumn(name = "MaSTK", nullable = false)
    private SoTietKiem soTietKiem;

    @Temporal(TemporalType.DATE)
    private Date ngayMoSTK;

    @OneToMany(mappedBy = "moSoTietKiem", cascade = CascadeType.ALL)
    private List<PhieuGuiTien> phieuGuiTienList;

    @OneToMany(mappedBy = "moSoTietKiem", cascade = CascadeType.ALL)
    private List<PhieuRutTien> phieuRutTienList;
}