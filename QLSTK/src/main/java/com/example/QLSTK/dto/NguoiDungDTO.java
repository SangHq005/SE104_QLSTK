package com.example.QLSTK.dto;

import lombok.Data;
import java.util.Date;

@Data
public class NguoiDungDTO {
    private String tenND;
    private String cccd;
    private String diaChi;
    private String sdt;
    private Date ngaySinh;
    private String email;
    private String matKhau;
    private int vaiTro;
}