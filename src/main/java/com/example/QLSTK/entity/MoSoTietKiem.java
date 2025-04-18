package com.example.QLSTK.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "MOSOTIETKIEM")
@Data
public class MoSoTietKiem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maMSTK;

    @ManyToOne
    @JoinColumn(name = "maKH")
    private NguoiDung nguoiDung;

    @ManyToOne
    @JoinColumn(name = "maSTK")
    private SoTietKiem soTietKiem;

    @Temporal(TemporalType.DATE)
    private Date ngayMoSTK;
}