package com.example.QLSTK.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "PHIEUGUITIEN")
@Data
public class PhieuGuiTien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maPGT;

    @ManyToOne
    @JoinColumn(name = "maMSTK")
    private MoSoTietKiem moSoTietKiem;

    private float soTienGui;

    @Temporal(TemporalType.DATE)
    private Date ngayGui;
}