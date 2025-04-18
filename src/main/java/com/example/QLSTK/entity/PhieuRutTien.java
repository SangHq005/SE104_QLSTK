package com.example.QLSTK.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "PHIEURUTTIEN")
@Data
public class PhieuRutTien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maPRT;

    @ManyToOne
    @JoinColumn(name = "maMSTK")
    private MoSoTietKiem moSoTietKiem;

    private float soTienRut;

    @Temporal(TemporalType.DATE)
    private Date ngayRut;
}