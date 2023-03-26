package com.ravioly.controlehotel.controller;

import com.ravioly.controlehotel.dto.HospedeDTO;
import com.ravioly.controlehotel.service.HospedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospedes")
public class HospedeController {


    @Autowired
    private HospedeService service;

    @PostMapping
    public HospedeDTO addHospede(@RequestBody HospedeDTO hospede) {
        return service.add(hospede);
    }

    @DeleteMapping("/{idHospede}")
    public void delete(@PathVariable Long idHospede) {
        service.delete(idHospede);
    }

    @GetMapping("/{idHospede}")
    public HospedeDTO getById(@PathVariable Long idHospede) {
        return service.getById(idHospede);
    }

    @PutMapping("/{idHospede}")
    public HospedeDTO update(@PathVariable Long idHospede, @RequestBody HospedeDTO hospede) {
        return service.update(idHospede, hospede);
    }

    @GetMapping
    public List<HospedeDTO> getByQuery(@RequestParam(required = false) String termo) {
        return service.getByQuery(termo);
    }
}
