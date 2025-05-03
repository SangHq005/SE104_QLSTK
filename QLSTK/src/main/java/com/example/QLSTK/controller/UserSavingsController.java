package com.example.QLSTK.controller;

import com.example.QLSTK.dto.DepositRequest;
import com.example.QLSTK.dto.MoSoTietKiemRequest;
import com.example.QLSTK.dto.WithdrawRequest;
import com.example.QLSTK.entity.MoSoTietKiem;
import com.example.QLSTK.entity.NguoiDung;
import com.example.QLSTK.service.MoSoTietKiemService;
import com.example.QLSTK.service.PhieuGuiTienService;
import com.example.QLSTK.service.PhieuRutTienService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Savings Management", description = "APIs for users to manage their savings accounts")
@RestController
@RequestMapping("/api/user/savings")
public class UserSavingsController {

    @Autowired
    private MoSoTietKiemService moSoTietKiemService;

    @Autowired
    private PhieuGuiTienService phieuGuiTienService;

    @Autowired
    private PhieuRutTienService phieuRutTienService;

    @Operation(summary = "Create a new savings account (User only)")
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> createSavingsAccount(@RequestBody MoSoTietKiemRequest request, Authentication authentication) {
        try {
            Integer userId = ((NguoiDung) authentication.getPrincipal()).getMaND();
            MoSoTietKiem moSoTietKiem = moSoTietKiemService.openSavingsAccount(userId, request.getSoTietKiemId(), request.getInitialDeposit());
            return ResponseEntity.status(HttpStatus.CREATED).body(moSoTietKiem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Get user's savings accounts (User only)")
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<MoSoTietKiem>> getUserSavingsAccounts(Authentication authentication) {
        Integer userId = ((NguoiDung) authentication.getPrincipal()).getMaND();
        return ResponseEntity.ok(moSoTietKiemService.getUserSavingsAccounts(userId));
    }

    @Operation(summary = "Get savings account details (User only)")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{moSoTietKiemId}")
    public ResponseEntity<?> getSavingsAccountDetails(@PathVariable Integer moSoTietKiemId, Authentication authentication) {
        try {
            Integer userId = ((NguoiDung) authentication.getPrincipal()).getMaND();
            MoSoTietKiem details = moSoTietKiemService.getSavingsAccountDetails(moSoTietKiemId, userId);
            return ResponseEntity.ok(details);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Deposit money (User only)")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{moSoTietKiemId}/deposit")
    public ResponseEntity<?> deposit(@PathVariable Integer moSoTietKiemId, @RequestBody DepositRequest request, Authentication authentication) {
        try {
            Integer userId = ((NguoiDung) authentication.getPrincipal()).getMaND();
            moSoTietKiemService.validateUserAccess(moSoTietKiemId, userId);
            return ResponseEntity.ok(phieuGuiTienService.deposit(moSoTietKiemId, request.getAmount()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Withdraw money (User only)")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{moSoTietKiemId}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable Integer moSoTietKiemId, @RequestBody WithdrawRequest request, Authentication authentication) {
        try {
            Integer userId = ((NguoiDung) authentication.getPrincipal()).getMaND();
            moSoTietKiemService.validateUserAccess(moSoTietKiemId, userId);
            return ResponseEntity.ok(phieuRutTienService.withdraw(moSoTietKiemId, request.getAmount()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}