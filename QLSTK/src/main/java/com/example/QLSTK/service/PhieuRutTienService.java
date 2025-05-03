package com.example.QLSTK.service;

import com.example.QLSTK.entity.MoSoTietKiem;
import com.example.QLSTK.entity.PhieuGuiTien;
import com.example.QLSTK.entity.PhieuRutTien;
import com.example.QLSTK.repository.MoSoTietKiemRepository;
import com.example.QLSTK.repository.PhieuRutTienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PhieuRutTienService {

    @Autowired
    private PhieuRutTienRepository phieuRutTienRepository;

    @Autowired
    private MoSoTietKiemRepository moSoTietKiemRepository;

    @Transactional
    public PhieuRutTien withdraw(Integer moSoTietKiemId, Float amount) throws Exception {
        Optional<MoSoTietKiem> moSoTietKiemOpt = moSoTietKiemRepository.findById(moSoTietKiemId);
        if (!moSoTietKiemOpt.isPresent()) {
            throw new Exception("Sổ tiết kiệm không tồn tại");
        }
        MoSoTietKiem moSoTietKiem = moSoTietKiemOpt.get();
        if (moSoTietKiem.getDaDong()) {
            throw new Exception("Sổ tiết kiệm đã đóng");
        }

        Float balance = calculateBalance(moSoTietKiem);
        Integer kyHan = moSoTietKiem.getSoTietKiem().getKyHan();
        long daysSinceOpened = (new Date().getTime() - moSoTietKiem.getNgayMoSTK().getTime()) / (1000 * 60 * 60 * 24);

        Integer thoiGianGuiToiThieu = moSoTietKiem.getSoTietKiem().getThoiGianGuiToiThieu();
        if (thoiGianGuiToiThieu == null) thoiGianGuiToiThieu = 15;

        if (daysSinceOpened < thoiGianGuiToiThieu) {
            throw new Exception("Chưa đủ thời gian gửi tối thiểu");
        }

        if (kyHan == 0) {
            if (amount > balance) {
                throw new Exception("Số tiền rút vượt quá số dư");
            }
        } else {
            if (daysSinceOpened < kyHan * 30) {
                throw new Exception("Chưa đến kỳ hạn rút tiền");
            }
            if (amount != balance) {
                throw new Exception("Phải rút toàn bộ số dư cho loại có kỳ hạn");
            }
        }

        PhieuRutTien phieuRutTien = new PhieuRutTien();
        phieuRutTien.setMoSoTietKiem(moSoTietKiem);
        phieuRutTien.setSoTienRut(amount);
        phieuRutTien.setNgayRut(new Date());
        phieuRutTienRepository.save(phieuRutTien);

        if (amount >= balance) {
            moSoTietKiem.setDaDong(true);
            moSoTietKiemRepository.save(moSoTietKiem);
        }

        return phieuRutTien;
    }

    public Float calculateBalance(MoSoTietKiem moSoTietKiem) {
        Float deposits = moSoTietKiem.getPhieuGuiTienList().stream()
                .map(PhieuGuiTien::getSoTienGui)
                .reduce(0f, Float::sum);
        Float withdrawals = moSoTietKiem.getPhieuRutTienList().stream()
                .map(PhieuRutTien::getSoTienRut)
                .reduce(0f, Float::sum);
        Float balance = deposits - withdrawals;

        Integer kyHan = moSoTietKiem.getSoTietKiem().getKyHan();
        Float laiSuat = moSoTietKiem.getSoTietKiem().getLaiSuat();
        long daysSinceOpened = (new Date().getTime() - moSoTietKiem.getNgayMoSTK().getTime()) / (1000 * 60 * 60 * 24);

        if (kyHan > 0 && daysSinceOpened < kyHan * 30) {
            laiSuat = 0.005f; // Use non-term interest rate if withdrawn before term
        }

        Float interest = balance * laiSuat * (daysSinceOpened / 365f);
        return balance + interest;
    }

    public List<PhieuRutTien> getAllWithdrawals() {
        return phieuRutTienRepository.findAll();
    }
}