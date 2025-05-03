package com.example.QLSTK.service;

import com.example.QLSTK.entity.MoSoTietKiem;
import com.example.QLSTK.entity.NguoiDung;
import com.example.QLSTK.entity.SoTietKiem;
import com.example.QLSTK.repository.MoSoTietKiemRepository;
import com.example.QLSTK.repository.NguoiDungRepository;
import com.example.QLSTK.repository.SoTietKiemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MoSoTietKiemService {

    @Autowired
    private MoSoTietKiemRepository moSoTietKiemRepository;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private SoTietKiemRepository soTietKiemRepository;

    @Autowired
    private PhieuGuiTienService phieuGuiTienService;

    @Transactional
    public MoSoTietKiem openSavingsAccount(Integer userId, Integer soTietKiemId, Float initialDeposit) throws Exception {
        Optional<NguoiDung> nguoiDungOpt = nguoiDungRepository.findById(userId);
        Optional<SoTietKiem> soTietKiemOpt = soTietKiemRepository.findById(soTietKiemId);
        if (!nguoiDungOpt.isPresent()) {
            throw new Exception("User not found");
        }
        if (!soTietKiemOpt.isPresent()) {
            throw new Exception("Savings type not found");
        }

        SoTietKiem soTietKiem = soTietKiemOpt.get();
        if (initialDeposit < soTietKiem.getSoTienGuiToiThieu()) {
            throw new Exception("Số tiền gửi ban đầu phải >= " + soTietKiem.getSoTienGuiToiThieu());
        }

        MoSoTietKiem moSoTietKiem = new MoSoTietKiem();
        moSoTietKiem.setNguoiDung(nguoiDungOpt.get());
        moSoTietKiem.setSoTietKiem(soTietKiem);
        moSoTietKiem.setNgayMoSTK(new Date());
        moSoTietKiem.setDaDong(false);
        moSoTietKiem = moSoTietKiemRepository.save(moSoTietKiem);

        // Record initial deposit
        phieuGuiTienService.deposit(moSoTietKiem.getMaMSTK(), initialDeposit);
        return moSoTietKiem;
    }

    public List<MoSoTietKiem> getUserSavingsAccounts(Integer userId) {
        return moSoTietKiemRepository.findByNguoiDung_MaND(userId);
    }

    public MoSoTietKiem getSavingsAccountDetails(Integer moSoTietKiemId, Integer userId) throws Exception {
        Optional<MoSoTietKiem> moSoTietKiemOpt = moSoTietKiemRepository.findById(moSoTietKiemId);
        if (!moSoTietKiemOpt.isPresent()) {
            throw new Exception("Sổ tiết kiệm không tồn tại");
        }
        MoSoTietKiem moSoTietKiem = moSoTietKiemOpt.get();
        if (!moSoTietKiem.getNguoiDung().getMaND().equals(userId)) {
            throw new Exception("Không có quyền truy cập sổ tiết kiệm này");
        }
        return moSoTietKiem;
    }

    public void validateUserAccess(Integer moSoTietKiemId, Integer userId) throws Exception {
        Optional<MoSoTietKiem> moSoTietKiemOpt = moSoTietKiemRepository.findById(moSoTietKiemId);
        if (!moSoTietKiemOpt.isPresent()) {
            throw new Exception("Sổ tiết kiệm không tồn tại");
        }
        if (!moSoTietKiemOpt.get().getNguoiDung().getMaND().equals(userId)) {
            throw new Exception("Không có quyền truy cập sổ tiết kiệm này");
        }
    }
}