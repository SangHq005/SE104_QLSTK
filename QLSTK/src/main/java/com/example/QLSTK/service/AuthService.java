package com.example.QLSTK.service;

import com.example.QLSTK.dto.*;
import com.example.QLSTK.entity.DangNhap;
import com.example.QLSTK.entity.NguoiDung;
import com.example.QLSTK.filter.JwtAuthenticationFilter;
import com.example.QLSTK.repository.DangNhapRepository;
import com.example.QLSTK.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private DangNhapRepository dangNhapRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JwtAuthenticationFilter jwtFilter;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Map<String, String> passcodeStore = new HashMap<>(); // Temporary passcode storage

    @Transactional
    public LoginResponse signIn(LoginRequest request) throws Exception {
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

        // Generate JWT token
        String token = jwtFilter.generateToken(nguoiDung.getEmail());
        LoginResponse response = new LoginResponse();
        response.setUser(userService.mapToUserResponse(nguoiDung));
        response.setToken(token);
        return response;
    }

    @Transactional
    public UserResponse signUp(RegisterRequest request) throws Exception {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new Exception("Mật khẩu xác nhận không khớp");
        }
        if (nguoiDungRepository.existsByEmail(request.getEmail())) {
            throw new Exception("Email đã được sử dụng");
        }
        if (nguoiDungRepository.existsBySdt(request.getPhoneNumber())) {
            throw new Exception("Số điện thoại đã được sử dụng");
        }

        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setEmail(request.getEmail());
        nguoiDung.setSdt(request.getPhoneNumber());
        nguoiDung.setMatKhau(passwordEncoder.encode(request.getPassword()));
        nguoiDung.setVaiTro(1); // Default to user role
        nguoiDung.setTenND("");
        nguoiDung.setCccd("");
        nguoiDung.setDiaChi("");
        nguoiDung.setNgaySinh(null);
        nguoiDung = nguoiDungRepository.save(nguoiDung);

        DangNhap dangNhap = new DangNhap();
        dangNhap.setNguoiDung(nguoiDung);
        dangNhap.setMatKhau(nguoiDung.getMatKhau());
        dangNhap.setLoginTime(new Date());
        dangNhapRepository.save(dangNhap);

        return userService.mapToUserResponse(nguoiDung);
    }

    public void sendPasscode(String email) throws Exception {
        Optional<NguoiDung> nguoiDungOpt = nguoiDungRepository.findByEmail(email);
        if (!nguoiDungOpt.isPresent()) {
            throw new Exception("Email không tồn tại");
        }

        String passcode = String.format("%06d", new Random().nextInt(999999));
        passcodeStore.put(email, passcode);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Passcode");
        message.setText("Your passcode is: " + passcode);
        mailSender.send(message);
    }

    public void verifyPasscode(String email, String passcode) throws Exception {
        String storedPasscode = passcodeStore.get(email);
        if (storedPasscode == null || !storedPasscode.equals(passcode)) {
            throw new Exception("Invalid passcode");
        }
    }

    @Transactional
    public void resetPassword(String email, String newPassword, String confirmPassword) throws Exception {
        if (!newPassword.equals(confirmPassword)) {
            throw new Exception("Mật khẩu xác nhận không khớp");
        }

        Optional<NguoiDung> nguoiDungOpt = nguoiDungRepository.findByEmail(email);
        if (!nguoiDungOpt.isPresent()) {
            throw new Exception("Email không tồn tại");
        }

        NguoiDung nguoiDung = nguoiDungOpt.get();
        nguoiDung.setMatKhau(passwordEncoder.encode(newPassword));
        nguoiDungRepository.save(nguoiDung);
        passcodeStore.remove(email);
    }
}