package com.example.QLSTK.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "NGUOIDUNG")
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maND;

    private String tenND;
    private String cccd;
    private String diaChi;
    private String sdt;
    @Temporal(TemporalType.DATE)
    private Date ngaySinh;
    private String email;
    private String matKhau;
    private Integer vaiTro; // 0: admin, 1: user

    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL)
    private List<DangNhap> dangNhapList;

    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL)
    private List<ThayDoi> thayDoiList;

    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL)
    private List<MoSoTietKiem> moSoTietKiemList;
}