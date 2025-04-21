package com.example.QLSTK.repository;

import com.example.QLSTK.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer> {
    Optional<NguoiDung> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsBySdt(String sdt);
}