package com.example.QLSTK.service;

import com.example.QLSTK.dto.UserResponse;
import com.example.QLSTK.entity.DangNhap;
import com.example.QLSTK.entity.NguoiDung;
import com.example.QLSTK.entity.PhieuGuiTien;
import com.example.QLSTK.repository.DangNhapRepository;
import com.example.QLSTK.repository.NguoiDungRepository;
import com.example.QLSTK.repository.PhieuGuiTienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private NguoiDungRepository nguoiDungRepository;
    @Autowired
    private DangNhapRepository dangNhapRepository;
    @Autowired
    private PhieuGuiTienRepository phieuGuiTienRepository;

    public UserResponse mapToUserResponse(NguoiDung nguoiDung) {
        UserResponse response = new UserResponse();
        response.setMaND(nguoiDung.getMaND());
        response.setTenND(nguoiDung.getTenND());
        response.setEmail(nguoiDung.getEmail());
        response.setSdt(nguoiDung.getSdt());
        response.setVaiTro(nguoiDung.getVaiTro());
        return response;
    }

    public List<UserResponse> getAllUsers() {
        return nguoiDungRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse updateUser(Integer maND, NguoiDung updatedUser) throws Exception {
        Optional<NguoiDung> nguoiDungOpt = nguoiDungRepository.findById(maND);
        if (!nguoiDungOpt.isPresent()) {
            throw new Exception("Người dùng không tồn tại");
        }
        NguoiDung nguoiDung = nguoiDungOpt.get();
        nguoiDung.setTenND(updatedUser.getTenND());
        nguoiDung.setCccd(updatedUser.getCccd());
        nguoiDung.setDiaChi(updatedUser.getDiaChi());
        nguoiDung.setSdt(updatedUser.getSdt());
        nguoiDung.setNgaySinh(updatedUser.getNgaySinh());
        nguoiDung.setEmail(updatedUser.getEmail());
        nguoiDung.setVaiTro(updatedUser.getVaiTro());
        nguoiDungRepository.save(nguoiDung);
        return mapToUserResponse(nguoiDung);
    }

    public Float getTotalRevenue() {
        return phieuGuiTienRepository.findAll().stream()
                .map(PhieuGuiTien::getSoTienGui)
                .reduce(0f, Float::sum);
    }

    public Long getDailyVisits() {
        Calendar calendar = Calendar.getInstance();
        return (long) dangNhapRepository.findByLoginDate(calendar.getTime()).size();
    }

    public Float getMonthlyRevenue() {
        Calendar calendar = Calendar.getInstance();
        return phieuGuiTienRepository.findByMonth(calendar.getTime()).stream()
                .map(PhieuGuiTien::getSoTienGui)
                .reduce(0f, Float::sum);
    }
}