package com.example.QLSTK.service;

import com.example.QLSTK.dto.DashboardSummaryDTO;
import com.example.QLSTK.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

@Service
public class DashboardService {

    @Autowired
    private TransactionRepository transactionRepository;

    public DashboardSummaryDTO getSummary(String email) {
        BigDecimal walletBalance = transactionRepository.getWalletBalanceByEmail(email);
        YearMonth currentMonth = YearMonth.now();
        LocalDate start = currentMonth.atDay(1);
        LocalDate end = currentMonth.atEndOfMonth();

        BigDecimal deposit = transactionRepository.sumByTypeAndDateRange(email, "DEPOSIT", start, end);
        BigDecimal withdraw = transactionRepository.sumByTypeAndDateRange(email, "WITHDRAW", start, end);

        return new DashboardSummaryDTO(walletBalance, withdraw, deposit);
    }
}
