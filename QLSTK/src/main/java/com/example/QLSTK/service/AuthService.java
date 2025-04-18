package com.example.QLSTK.service;

import com.example.QLSTK.dto.LoginRequest;
import com.example.QLSTK.dto.LoginResponse;
import com.example.QLSTK.dto.NguoiDungDTO;
import com.example.QLSTK.entity.NguoiDung;
import com.example.QLSTK.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(NguoiDungDTO dto) {
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setTenND(dto.getTenND());
        nguoiDung.setCccd(dto.getCccd());
        nguoiDung.setDiaChi(dto.getDiaChi());
        nguoiDung.setSdt(dto.getSdt());
        nguoiDung.setNgaySinh(dto.getNgaySinh());
        nguoiDung.setEmail(dto.getEmail());
        nguoiDung.setMatKhau(passwordEncoder.encode(dto.getMatKhau()));
        nguoiDung.setVaiTro(dto.getVaiTro());
        nguoiDungRepository.save(nguoiDung);
    }

    public LoginResponse login(LoginRequest request) {
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(request.getEmail());
        if (nguoiDung != null && passwordEncoder.matches(request.getMatKhau(), nguoiDung.getMatKhau())) {
            LoginResponse response = new LoginResponse();
            response.setEmail(nguoiDung.getEmail());
            response.setVaiTro(nguoiDung.getVaiTro());
            response.setToken("dummy-token"); // Thay bằng JWT nếu cần
            return response;
        }
        throw new RuntimeException("Invalid credentials");
    }
}