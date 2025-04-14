package com.example.QLSTK.services;

import com.example.QLSTK.entities.NguoiDung;
import com.example.QLSTK.repositories.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NguoiDungService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    public List<NguoiDung> getAll() {
        return nguoiDungRepository.findAll();
    }

    public Optional<NguoiDung> getById(int id) {
        return nguoiDungRepository.findById(id);
    }

    public NguoiDung create(NguoiDung nguoiDung) {
        return nguoiDungRepository.save(nguoiDung);
    }

    public NguoiDung update(int id, NguoiDung updatedNguoiDung) {
        Optional<NguoiDung> existing = nguoiDungRepository.findById(id);
        if (existing.isPresent()) {
            updatedNguoiDung.setMaND(id);
            return nguoiDungRepository.save(updatedNguoiDung);
        } else {
            return null;
        }
    }

    public void delete(int id) {
        nguoiDungRepository.deleteById(id);
    }
}
