package com.example.QLSTK.repository;

import com.example.QLSTK.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer> {
    NguoiDung findByEmail(String email);
}