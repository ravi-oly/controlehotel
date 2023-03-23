package com.ravioly.controlehotel.service;

import com.ravioly.controlehotel.dto.HospedeRegistroDTO;
import com.ravioly.controlehotel.entity.RegistroEntity;
import com.ravioly.controlehotel.repository.HospedeRepository;
import com.ravioly.controlehotel.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultasService {
    @Autowired
    private HospedeRepository hospedeRepository;

    @Autowired
    private RegistroRepository registroRepository;

    public List<HospedeRegistroDTO> getAll() {
        return hospedeRepository.findAll().stream().map(h -> {
            List<RegistroEntity> registros = registroRepository.findAllByHospede_IdAndDataSaidaNotNullOrderByDataEntradaDesc(h.getId());
            return new HospedeRegistroDTO(h.getNome(), h.getDocumento(),
                    h.getTotalGasto() != null ? h.getTotalGasto() : 0, registros.isEmpty() ? 0 : registros.get(0).getValorTotal());
        }).collect(Collectors.toList());
    }

    public List<HospedeRegistroDTO> getByDataSaidaNull() {
        return hospedeRepository.findAllByRegistros_DataSaidaNull().stream().map(h -> {
            List<RegistroEntity> registros = registroRepository.findAllByHospede_IdAndDataSaidaNotNullOrderByDataEntradaDesc(h.getId());
            return new HospedeRegistroDTO(h.getNome(), h.getDocumento(),
                    h.getTotalGasto() != null ? h.getTotalGasto() : 0, registros.isEmpty() ? 0 : registros.get(0).getValorTotal());
        }).collect(Collectors.toList());
    }

    public List<HospedeRegistroDTO> getByDataSaidaNotNull() {
        return hospedeRepository.findAllByRegistros_DataSaidaNotNull().stream().map(h -> {
            List<RegistroEntity> registros = registroRepository.findAllByHospede_IdAndDataSaidaNotNullOrderByDataEntradaDesc(h.getId());
            return new HospedeRegistroDTO(h.getNome(), h.getDocumento(),
                    h.getTotalGasto() != null ? h.getTotalGasto() : 0, registros.isEmpty() ? 0 : registros.get(0).getValorTotal());
        }).collect(Collectors.toList());
    }
}
