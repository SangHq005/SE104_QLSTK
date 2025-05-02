package com.example.QLSTK.controller;

import com.example.QLSTK.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Transaction Management", description = "APIs for viewing transaction details")
@RestController
@RequestMapping("/api/admin/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "Get transactions by savings account (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{moSoTietKiemId}")
    public ResponseEntity<?> getTransactions(@PathVariable Integer moSoTietKiemId) {
        try {
            List<Object> transactions = transactionService.getTransactionsByMoSoTietKiem(moSoTietKiemId);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}