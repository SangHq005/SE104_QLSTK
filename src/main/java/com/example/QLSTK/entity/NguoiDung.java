package com.example.QLSTK.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "NGUOIDUNG")
@Data
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maND;

    private String tenND;
    private String cccd;
    private String diaChi;
    private String sdt;
    @Temporal(TemporalType.DATE)
    private Date ngaySinh;
    private String email;
    private String matKhau;
    private boolean vaiTro; // 0-admin, 1-user
    
    public int getVaiTro() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVaiTro'");
    }

    public void setVaiTro(int vaiTro2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setVaiTro'");
    }
}