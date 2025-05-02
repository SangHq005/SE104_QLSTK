package com.example.QLSTK.service;

import com.example.QLSTK.entity.MoSoTietKiem;
import com.example.QLSTK.entity.PhieuGuiTien;
import com.example.QLSTK.entity.PhieuRutTien;
import com.example.QLSTK.repository.MoSoTietKiemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private MoSoTietKiemRepository moSoTietKiemRepository;

    public List<Object> getTransactionsByMoSoTietKiem(Integer moSoTietKiemId) throws Exception {
        Optional<MoSoTietKiem> moSoTietKiemOpt = moSoTietKiemRepository.findById(moSoTietKiemId);
        if (!moSoTietKiemOpt.isPresent()) {
            throw new Exception("Sổ tiết kiệm không tồn tại");
        }
        MoSoTietKiem moSoTietKiem = moSoTietKiemOpt.get();

        List<Object> transactions = new ArrayList<>();
        transactions.addAll(moSoTietKiem.getPhieuGuiTienList());
        transactions.addAll(moSoTietKiem.getPhieuRutTienList());
        return transactions;
    }
}