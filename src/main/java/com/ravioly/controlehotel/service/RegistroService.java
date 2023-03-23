package com.ravioly.controlehotel.service;

import com.ravioly.controlehotel.dto.HospedeDTO;
import com.ravioly.controlehotel.dto.RegistroDTO;
import com.ravioly.controlehotel.entity.HospedeEntity;
import com.ravioly.controlehotel.entity.RegistroEntity;
import com.ravioly.controlehotel.repository.HospedeRepository;
import com.ravioly.controlehotel.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistroService {

    @Autowired
    private RegistroRepository repository;

    @Autowired
    private HospedeRepository hospedeRepository;

    private Double getValorDia(LocalDate date, Boolean adcCarro){
        Double val = 0.0;
        if(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY ) { // se for final de semana
            val += 150.00;
            if(adcCarro) {
                val += 20.00;
            }
        } else { // se for dia de semana
            val += 120.00;
            if(adcCarro) {
                val += 15.00;
            }
        }
        return val;
    }

    private Double calcularValorRegistro(RegistroEntity registro) {
        Double valorReg = 0.0;
        for (LocalDate date = registro.getDataEntrada().toLocalDate(); date.isBefore(registro.getDataSaida().toLocalDate()) || date.isEqual(registro.getDataSaida().toLocalDate()); date = date.plusDays(1)) { // adiciona ao valor da diaria por dia entre a data de entrada e saída
            valorReg += getValorDia(date, registro.getAdicionalVeiculo());
        }
        if(registro.getDataSaida().toLocalTime().isAfter(LocalTime.of(16, 30))) {  //valida se a saída foi após as 16:30
            valorReg += getValorDia(registro.getDataSaida().toLocalDate().plusDays(1), false);
        }
        return valorReg;
    }

    public RegistroDTO add(RegistroDTO registro) {
        RegistroEntity e = new RegistroEntity();
        e.setDataEntrada(registro.getDataEntrada());
        e.setAdicionalVeiculo(registro.getAdicionalVeiculo());

        HospedeEntity h = hospedeRepository.findById(registro.getHospede().getId()).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        e.setHospede(h);
        if (registro.getDataSaida() != null) {
            e.setDataSaida(registro.getDataSaida());
            e.setValorTotal(calcularValorRegistro(e));
            h.setTotalGasto(h.getTotalGasto() != null ? h.getTotalGasto()+e.getValorTotal() : e.getValorTotal());
        }
        e = repository.save(e);
        h = hospedeRepository.save(h);

        return new RegistroDTO(e.getId(), new HospedeDTO(h.getId(), h.getNome(), h.getDocumento(), h.getTelefone()),
                e.getDataEntrada(), e.getDataSaida(), e.getAdicionalVeiculo());

    }

    public RegistroDTO update(Long id, LocalDateTime dataSaida) {
        RegistroEntity e = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(e.getDataSaida() != null) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }
        HospedeEntity h = e.getHospede();
        if (dataSaida != null) {
            e.setDataSaida(dataSaida);
            e.setValorTotal(calcularValorRegistro(e));
            h.setTotalGasto(h.getTotalGasto() != null ? h.getTotalGasto()+e.getValorTotal() : e.getValorTotal());
        }
        e = repository.save(e);
        h = hospedeRepository.save(h);

        return new RegistroDTO(e.getId(), new HospedeDTO(h.getId(), h.getNome(), h.getDocumento(), h.getTelefone()),
                e.getDataEntrada(), e.getDataSaida(), e.getAdicionalVeiculo());

    }

    public List<RegistroDTO> getAll() {
        return repository.findAll().stream().map(e -> {
                HospedeEntity h = e.getHospede();
                return new RegistroDTO(e.getId(), new HospedeDTO(h.getId(), h.getNome(), h.getDocumento(), h.getTelefone()),
                        e.getDataEntrada(), e.getDataSaida(), e.getAdicionalVeiculo());
                }).
                collect(Collectors.toList());
    }

    public RegistroDTO getById(Long id) {
        RegistroEntity e = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        HospedeEntity h = e.getHospede();
        return new RegistroDTO(e.getId(), new HospedeDTO(h.getId(), h.getNome(), h.getDocumento(), h.getTelefone()),
                e.getDataEntrada(), e.getDataSaida(), e.getAdicionalVeiculo());
    }
}
