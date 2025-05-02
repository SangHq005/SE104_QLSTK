package com.example.QLSTK.repository;

import com.example.QLSTK.entity.PhieuGuiTien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PhieuGuiTienRepository extends JpaRepository<PhieuGuiTien, Integer> {
    @Query("SELECT p FROM PhieuGuiTien p WHERE MONTH(p.ngayGui) = MONTH(:date) AND YEAR(p.ngayGui) = YEAR(:date)")
    List<PhieuGuiTien> findByMonth(Date date);
}