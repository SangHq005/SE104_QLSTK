package com.example.QLSTK.service;

import com.example.QLSTK.entity.MoSoTietKiem;
import com.example.QLSTK.entity.PhieuGuiTien;
import com.example.QLSTK.repository.MoSoTietKiemRepository;
import com.example.QLSTK.repository.PhieuGuiTienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PhieuGuiTienService {
    @Autowired
    private PhieuGuiTienRepository phieuGuiTienRepository;
    @Autowired
    private MoSoTietKiemRepository moSoTietKiemRepository;

    @Transactional
    public PhieuGuiTien deposit(Integer moSoTietKiemId, Float amount) throws Exception {
        Optional<MoSoTietKiem> moSoTietKiemOpt = moSoTietKiemRepository.findById(moSoTietKiemId);
        if (!moSoTietKiemOpt.isPresent()) {
            throw new Exception("Sổ tiết kiệm không tồn tại");
        }
        MoSoTietKiem moSoTietKiem = moSoTietKiemOpt.get();
        if (moSoTietKiem.getDaDong()) {
            throw new Exception("Sổ tiết kiệm đã đóng");
        }
        if (amount < 100000) {
            throw new Exception("Số tiền gửi phải >= 100.000đ");
        }

        // Kiểm tra kỳ hạn
        Integer kyHan = moSoTietKiem.getSoTietKiem().getKyHan();
        if (kyHan > 0) {
            long daysSinceOpened = (new Date().getTime() - moSoTietKiem.getNgayMoSTK().getTime()) / (1000 * 60 * 60 * 24);
            if (daysSinceOpened % (kyHan * 30) != 0) {
                throw new Exception("Chỉ được gửi thêm khi đến kỳ hạn");
            }
        }

        PhieuGuiTien phieuGuiTien = new PhieuGuiTien();
        phieuGuiTien.setMoSoTietKiem(moSoTietKiem);
        phieuGuiTien.setSoTienGui(amount);
        phieuGuiTien.setNgayGui(new Date());
        return phieuGuiTienRepository.save(phieuGuiTien);
    }
}