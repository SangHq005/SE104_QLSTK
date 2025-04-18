package com.example.QLSTK.config;

import com.example.QLSTK.entity.NguoiDung;
import com.example.QLSTK.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;


import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private NguoiDungRepository nguoiDungRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NguoiDung user = nguoiDungRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với email: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // hoặc user.getTenDangNhap() nếu muốn dùng tên đăng nhập
                user.getMatKhau(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getVaiTro()))
        );
    }
}
