package com.ravioly.controlehotel.controller;

import com.ravioly.controlehotel.dto.RegistroDTO;
import com.ravioly.controlehotel.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/registro")
public class RegistroController {

    @Autowired
    private RegistroService service;

    @PostMapping
    public RegistroDTO add(@RequestBody RegistroDTO dto) {
        return service.add(dto);
    }

    @PutMapping("/{id}")
    public  RegistroDTO update(@PathVariable Long id, @RequestBody LocalDateTime dataSaida) {
        return service.update(id, dataSaida);
    }

    @GetMapping
    public List<RegistroDTO> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public RegistroDTO getById(@PathVariable Long id){
        return service.getById(id);
    }
}
