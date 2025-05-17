package com.example.QLSTK.dto;

import java.math.BigDecimal;

public class DashboardSummaryDTO {
    private BigDecimal walletBalance;
    private BigDecimal withdrawThisMonth;
    private BigDecimal depositThisMonth;

    public DashboardSummaryDTO(BigDecimal walletBalance, BigDecimal withdrawThisMonth, BigDecimal depositThisMonth) {
        this.walletBalance = walletBalance;
        this.withdrawThisMonth = withdrawThisMonth;
        this.depositThisMonth = depositThisMonth;
    }

    // Getters and Setters
}
