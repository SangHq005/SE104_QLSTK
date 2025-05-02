package com.example.QLSTK.service;

import com.example.QLSTK.entity.DangNhap;
import com.example.QLSTK.entity.NguoiDung;
import com.example.QLSTK.dto.LoginRequest;
import com.example.QLSTK.dto.RegisterRequest;
import com.example.QLSTK.dto.UserResponse;
import com.example.QLSTK.repository.DangNhapRepository;
import com.example.QLSTK.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private NguoiDungRepository nguoiDungRepository;
    @Autowired
    private DangNhapRepository dangNhapRepository;
    @Autowired
    private UserService userService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public UserResponse signIn(LoginRequest request) throws Exception {
        Optional<NguoiDung> nguoiDungOpt = nguoiDungRepository.findByEmail(request.getEmail());
        if (!nguoiDungOpt.isPresent()) {
            throw new Exception("Email không tồn tại");
        }
        NguoiDung nguoiDung = nguoiDungOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), nguoiDung.getMatKhau())) {
            throw new Exception("Mật khẩu không đúng");
        }
        // Log login attempt
        DangNhap dangNhap = new DangNhap();
        dangNhap.setNguoiDung(nguoiDung);
        dangNhap.setMatKhau(nguoiDung.getMatKhau());
        dangNhap.setLoginTime(new Date());
        dangNhapRepository.save(dangNhap);
        return userService.mapToUserResponse(nguoiDung);
    }

    @Transactional
    public UserResponse signUp(RegisterRequest request) throws Exception {
        // Validate passwords match
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new Exception("Mật khẩu xác nhận không khớp");
        }
        // Check if email or phone number exists
        if (nguoiDungRepository.existsByEmail(request.getEmail())) {
            throw new Exception("Email đã được sử dụng");
        }
        if (nguoiDungRepository.existsBySdt(request.getPhoneNumber())) {
            throw new Exception("Số điện thoại đã được sử dụng");
        }
        // Create new user
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setEmail(request.getEmail());
        nguoiDung.setSdt(request.getPhoneNumber());
        nguoiDung.setMatKhau(passwordEncoder.encode(request.getPassword()));
        nguoiDung.setVaiTro(1); // Default to user role
        nguoiDung.setTenND(""); // Placeholder, can be updated later
        nguoiDung.setCccd("");
        nguoiDung.setDiaChi("");
        nguoiDung.setNgaySinh(null);
        nguoiDung = nguoiDungRepository.save(nguoiDung);
        // Log signup as a login attempt
        DangNhap dangNhap = new DangNhap();
        dangNhap.setNguoiDung(nguoiDung);
        dangNhap.setMatKhau(nguoiDung.getMatKhau());
        dangNhap.setLoginTime(new Date());
        dangNhapRepository.save(dangNhap);
        return userService.mapToUserResponse(nguoiDung);
    }
}