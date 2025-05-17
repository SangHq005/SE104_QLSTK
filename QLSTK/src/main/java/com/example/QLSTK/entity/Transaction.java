package com.example.QLSTK.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.QLSTK.model.TransactionType;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type; // DEPOSIT, WITHDRAW

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private NguoiDung user;

    // Constructors
    public Transaction() {}

    public Transaction(BigDecimal amount, TransactionType type, LocalDate date, NguoiDung user) {
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.user = user;
    }

    // Getters and Setters
}
