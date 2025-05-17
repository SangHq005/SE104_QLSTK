package com.example.QLSTK.repository;

import com.example.QLSTK.entity.Transaction;
import com.example.QLSTK.model.TransactionType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user.email = :email AND t.type = :type AND t.date BETWEEN :start AND :end")
    BigDecimal sumByTypeAndDateRange(
        @Param("email") String email,
        @Param("type") String type,
        @Param("start") LocalDate start,
        @Param("end") LocalDate end
    );

    @Query("SELECT COALESCE(SUM(CASE WHEN t.type = 'DEPOSIT' THEN t.amount ELSE -t.amount END), 0) FROM Transaction t WHERE t.user.email = :email")
    BigDecimal getWalletBalanceByEmail(@Param("email") String email);
}
