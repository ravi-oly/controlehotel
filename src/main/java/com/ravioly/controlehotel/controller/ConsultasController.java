package com.ravioly.controlehotel.controller;

import com.ravioly.controlehotel.dto.HospedeRegistroDTO;
import com.ravioly.controlehotel.service.ConsultasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultasController {

    @Autowired
    private ConsultasService service;

    @GetMapping
    public List<HospedeRegistroDTO> getAll(){
        return service.getAll();
    }

    @GetMapping("/registro-em-aberto")
    public List<HospedeRegistroDTO> getByDataSaidaNull(){
        return service.getByDataSaidaNull();
    }

    @GetMapping("/checkout")
    public List<HospedeRegistroDTO> getByDataSaidaNotNull(){
        return service.getByDataSaidaNotNull();
    }
}
