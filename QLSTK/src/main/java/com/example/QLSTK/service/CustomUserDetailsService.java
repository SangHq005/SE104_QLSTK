package com.example.QLSTK.service;

import com.example.QLSTK.entity.NguoiDung;
import com.example.QLSTK.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        String role = nguoiDung.getVaiTro() == 0 ? "ROLE_ADMIN" : "ROLE_USER";
        return new User(
                nguoiDung.getEmail(),
                nguoiDung.getMatKhau(),
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}