package com.example.QLSTK.repository;

import com.example.QLSTK.entity.DangNhap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface DangNhapRepository extends JpaRepository<DangNhap, Integer> {
    @Query("SELECT d FROM DangNhap d WHERE DATE(d.loginTime) = :date")
    List<DangNhap> findByLoginDate(Date date);
}