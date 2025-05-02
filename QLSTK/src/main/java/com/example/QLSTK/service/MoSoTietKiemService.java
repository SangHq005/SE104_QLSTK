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

    @Transactional
    public MoSoTietKiem openSavingsAccount(Integer userId, Integer soTietKiemId) throws Exception {
        Optional<NguoiDung> nguoiDungOpt = nguoiDungRepository.findById(userId);
        Optional<SoTietKiem> soTietKiemOpt = soTietKiemRepository.findById(soTietKiemId);

        if (!nguoiDungOpt.isPresent()) {
            throw new Exception("User not found");
        }
        if (!soTietKiemOpt.isPresent()) {
            throw new Exception("Not found this type of savings");
        }

        MoSoTietKiem moSoTietKiem = new MoSoTietKiem();
        moSoTietKiem.setNguoiDung(nguoiDungOpt.get());
        moSoTietKiem.setSoTietKiem(soTietKiemOpt.get());
        moSoTietKiem.setNgayMoSTK(new Date());
        moSoTietKiem.setDaDong(false);
        return moSoTietKiemRepository.save(moSoTietKiem);
    }

    public List<MoSoTietKiem> getUserSavingsAccounts(Integer userId) {
        return moSoTietKiemRepository.findByNguoiDung_MaND(userId);
    }
}