package com.example.QLSTK.controllers;

import com.example.QLSTK.entities.NguoiDung;
import com.example.QLSTK.services.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/nguoidung")
@CrossOrigin(origins = "*") // Nếu bạn dùng frontend tách biệt
public class NguoiDungController {

    @Autowired
    private NguoiDungService service;

    @GetMapping
    public List<NguoiDung> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<NguoiDung> getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public NguoiDung create(@RequestBody NguoiDung nguoiDung) {
        return service.create(nguoiDung);
    }

    @PutMapping("/{id}")
    public NguoiDung update(@PathVariable int id, @RequestBody NguoiDung nguoiDung) {
        return service.update(id, nguoiDung);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
