package com.example.QLSTK.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "PHIEURUTTIEN")
public class PhieuRutTien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maPRT;

    @ManyToOne
    @JoinColumn(name = "MaMSTK", nullable = false)
    private MoSoTietKiem moSoTietKiem;

    private Float soTienRut;

    @Temporal(TemporalType.DATE)
    private Date ngayRut;
}