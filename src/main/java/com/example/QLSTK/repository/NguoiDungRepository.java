package com.example.QLSTK.repository;

import com.example.QLSTK.entity.NguoiDung;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer> {
    Optional<NguoiDung> findByEmail(String email);
}