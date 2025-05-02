package com.example.QLSTK.repository;

import com.example.QLSTK.entity.MoSoTietKiem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoSoTietKiemRepository extends JpaRepository<MoSoTietKiem, Integer> {
    List<MoSoTietKiem> findByNguoiDung_MaND(Integer maND);
}