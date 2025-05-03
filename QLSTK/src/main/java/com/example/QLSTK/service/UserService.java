package com.example.QLSTK.service;

import com.example.QLSTK.dto.UserResponse;
import com.example.QLSTK.entity.MoSoTietKiem;
import com.example.QLSTK.entity.NguoiDung;
import com.example.QLSTK.entity.PhieuGuiTien;
import com.example.QLSTK.entity.PhieuRutTien;
import com.example.QLSTK.repository.DangNhapRepository;
import com.example.QLSTK.repository.MoSoTietKiemRepository;
import com.example.QLSTK.repository.NguoiDungRepository;
import com.example.QLSTK.repository.PhieuGuiTienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private DangNhapRepository dangNhapRepository;

    @Autowired
    private PhieuGuiTienRepository phieuGuiTienRepository;

    @Autowired
    private MoSoTietKiemRepository moSoTietKiemRepository;

    @Autowired
    private PhieuRutTienService phieuRutTienService;

    public UserResponse mapToUserResponse(NguoiDung nguoiDung) {
        UserResponse response = new UserResponse();
        response.setMaND(nguoiDung.getMaND());
        response.setTenND(nguoiDung.getTenND());
        response.setEmail(nguoiDung.getEmail());
        response.setSdt(nguoiDung.getSdt());
        response.setVaiTro(nguoiDung.getVaiTro());
        return response;
    }

    public List<UserResponse> getAllUsers() {
        return nguoiDungRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse updateUser(Integer maND, NguoiDung updatedUser) throws Exception {
        Optional<NguoiDung> nguoiDungOpt = nguoiDungRepository.findById(maND);
        if (!nguoiDungOpt.isPresent()) {
            throw new Exception("Người dùng không tồn tại");
        }
        NguoiDung nguoiDung = nguoiDungOpt.get();
        nguoiDung.setTenND(updatedUser.getTenND());
        nguoiDung.setCccd(updatedUser.getCccd());
        nguoiDung.setDiaChi(updatedUser.getDiaChi());
        nguoiDung.setSdt(updatedUser.getSdt());
        nguoiDung.setNgaySinh(updatedUser.getNgaySinh());
        nguoiDung.setEmail(updatedUser.getEmail());
        if (updatedUser.getVaiTro() != null) {
            nguoiDung.setVaiTro(updatedUser.getVaiTro());
        }
        nguoiDungRepository.save(nguoiDung);
        return mapToUserResponse(nguoiDung);
    }

    @Transactional
    public void deleteUser(Integer maND) throws Exception {
        Optional<NguoiDung> nguoiDungOpt = nguoiDungRepository.findById(maND);
        if (!nguoiDungOpt.isPresent()) {
            throw new Exception("Người dùng không tồn tại");
        }
        nguoiDungRepository.deleteById(maND);
    }

    public UserResponse getUserProfile(String email) throws Exception {
        Optional<NguoiDung> nguoiDungOpt = nguoiDungRepository.findByEmail(email);
        if (!nguoiDungOpt.isPresent()) {
            throw new Exception("Người dùng không tồn tại");
        }
        return mapToUserResponse(nguoiDungOpt.get());
    }

    public Float getTotalRevenue() {
        return phieuGuiTienRepository.findAll().stream()
                .map(PhieuGuiTien::getSoTienGui)
                .reduce(0f, Float::sum);
    }

    public Float getDailyRevenue() {
        Calendar calendar = Calendar.getInstance();
        return phieuGuiTienRepository.findAll().stream()
                .filter(p -> {
                    Calendar pCal = Calendar.getInstance();
                    pCal.setTime(p.getNgayGui());
                    return pCal.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                           pCal.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR);
                })
                .map(PhieuGuiTien::getSoTienGui)
                .reduce(0f, Float::sum);
    }

    public Float getMonthlyRevenue() {
        Calendar calendar = Calendar.getInstance();
        return phieuGuiTienRepository.findByMonth(calendar.getTime()).stream()
                .map(PhieuGuiTien::getSoTienGui)
                .reduce(0f, Float::sum);
    }

    public Long getDailyVisits() {
        Calendar calendar = Calendar.getInstance();
        return (long) dangNhapRepository.findByLoginDate(calendar.getTime()).size();
    }

    public List<Object> getAllTransactions() {
        List<Object> transactions = new ArrayList<>();
        transactions.addAll(phieuGuiTienRepository.findAll());
        transactions.addAll(phieuRutTienService.getAllWithdrawals());
        return transactions;
    }

    public Map<String, Object> getUserDashboard(Integer userId) throws Exception {
        Optional<NguoiDung> nguoiDungOpt = nguoiDungRepository.findById(userId);
        if (!nguoiDungOpt.isPresent()) {
            throw new Exception("Người dùng không tồn tại");
        }

        List<MoSoTietKiem> savingsAccounts = moSoTietKiemRepository.findByNguoiDung_MaND(userId);
        Float totalBalance = 0f;
        Float totalDeposited = 0f;
        Float totalWithdrawn = 0f;
        List<Object> transactions = new ArrayList<>();

        for (MoSoTietKiem account : savingsAccounts) {
            Float balance = phieuRutTienService.calculateBalance(account);
            totalBalance += balance;
            totalDeposited += account.getPhieuGuiTienList().stream()
                    .map(PhieuGuiTien::getSoTienGui)
                    .reduce(0f, Float::sum);
            totalWithdrawn += account.getPhieuRutTienList().stream()
                    .map(PhieuRutTien::getSoTienRut)
                    .reduce(0f, Float::sum);
            transactions.addAll(account.getPhieuGuiTienList());
            transactions.addAll(account.getPhieuRutTienList());
        }

        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("totalBalance", totalBalance);
        dashboard.put("totalDeposited", totalDeposited);
        dashboard.put("totalWithdrawn", totalWithdrawn);
        dashboard.put("transactions", transactions);
        return dashboard;
    }
}