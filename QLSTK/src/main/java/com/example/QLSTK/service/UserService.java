package com.example.QLSTK.service;

import com.example.QLSTK.entity.NguoiDung;
import com.example.QLSTK.dto.UserResponse;
import com.example.QLSTK.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    public UserResponse mapToUserResponse(NguoiDung nguoiDung) {
        UserResponse response = new UserResponse();
        response.setMaND(nguoiDung.getMaND());
        response.setTenND(nguoiDung.getTenND());
        response.setEmail(nguoiDung.getEmail());
        response.setSdt(nguoiDung.getSdt());
        response.setVaiTro(nguoiDung.getVaiTro());
        return response;
    }
}