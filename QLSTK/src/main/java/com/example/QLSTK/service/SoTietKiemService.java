package com.example.QLSTK.service;

import com.example.QLSTK.entity.SoTietKiem;
import com.example.QLSTK.entity.ThayDoi;
import com.example.QLSTK.repository.NguoiDungRepository;
import com.example.QLSTK.repository.SoTietKiemRepository;
import com.example.QLSTK.repository.ThayDoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SoTietKiemService {
    @Autowired
    private SoTietKiemRepository soTietKiemRepository;
    @Autowired
    private ThayDoiRepository thayDoiRepository;
    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    public List<SoTietKiem> getAllSoTietKiem() {
        return soTietKiemRepository.findAll();
    }

    @Transactional
    public SoTietKiem createSoTietKiem(SoTietKiem soTietKiem, Integer adminId) throws Exception {
        validateSoTietKiem(soTietKiem);
        SoTietKiem saved = soTietKiemRepository.save(soTietKiem);
        logThayDoi(saved, adminId);
        return saved;
    }

    @Transactional
    public SoTietKiem updateSoTietKiem(Integer id, SoTietKiem updated, Integer adminId) throws Exception {
        Optional<SoTietKiem> existingOpt = soTietKiemRepository.findById(id);
        if (!existingOpt.isPresent()) {
            throw new Exception("Loại tiết kiệm không tồn tại");
        }
        validateSoTietKiem(updated);
        SoTietKiem soTietKiem = existingOpt.get();
        soTietKiem.setTenSTK(updated.getTenSTK());
        soTietKiem.setSoTienGuiToiThieu(updated.getSoTienGuiToiThieu());
        soTietKiem.setKyHan(updated.getKyHan());
        soTietKiem.setLaiSuat(updated.getLaiSuat());
        soTietKiem.setThoiGianGuiToiThieu(updated.getThoiGianGuiToiThieu());
        SoTietKiem saved = soTietKiemRepository.save(soTietKiem);
        logThayDoi(saved, adminId);
        return saved;
    }

    @Transactional
    public void deleteSoTietKiem(Integer id, Integer adminId) throws Exception {
        Optional<SoTietKiem> existingOpt = soTietKiemRepository.findById(id);
        if (!existingOpt.isPresent()) {
            throw new Exception("Loại tiết kiệm không tồn tại");
        }
        soTietKiemRepository.deleteById(id);
        logThayDoi(existingOpt.get(), adminId);
    }

    private void validateSoTietKiem(SoTietKiem soTietKiem) throws Exception {
        if (soTietKiem.getSoTienGuiToiThieu() < 1000000) {
            throw new Exception("Số tiền gửi tối thiểu phải >= 1.000.000đ");
        }
        if (!List.of(0, 3, 6).contains(soTietKiem.getKyHan())) {
            throw new Exception("Kỳ hạn phải là 0, 3 hoặc 6 tháng");
        }
        if (soTietKiem.getLaiSuat() <= 0) {
            throw new Exception("Lãi suất phải lớn hơn 0");
        }
        if (soTietKiem.getThoiGianGuiToiThieu() < 0) {
            throw new Exception("Thời gian gửi tối thiểu không hợp lệ");
        }
    }

    private void logThayDoi(SoTietKiem soTietKiem, Integer adminId) {
        ThayDoi thayDoi = new ThayDoi();
        thayDoi.setSoTietKiem(soTietKiem);
        thayDoi.setSoTienGuiToiThieu(soTietKiem.getSoTienGuiToiThieu());
        thayDoi.setKyHan(soTietKiem.getKyHan());
        thayDoi.setLaiSuat(soTietKiem.getLaiSuat());
        nguoiDungRepository.findById(adminId).ifPresent(thayDoi::setNguoiDung);
        thayDoiRepository.save(thayDoi);
    }
}