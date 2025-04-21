package com.example.QLSTK.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "PHIEUGUITIEN")
public class PhieuGuiTien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maPGT;

    @ManyToOne
    @JoinColumn(name = "MaMSTK", nullable = false)
    private MoSoTietKiem moSoTietKiem;

    private Float soTienGui;

    @Temporal(TemporalType.DATE)
    private Date ngayGui;
}