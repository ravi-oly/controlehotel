package com.ravioly.controlehotel.service;

import com.ravioly.controlehotel.dto.HospedeDTO;
import com.ravioly.controlehotel.entity.HospedeEntity;
import com.ravioly.controlehotel.repository.HospedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospedeService {

    @Autowired
    private HospedeRepository repository;

    public List<HospedeDTO> getAll() {
        return repository.findAll().stream().map(h -> new HospedeDTO(h.getId(), h.getNome(), h.getDocumento(), h.getTelefone())).
                collect(Collectors.toList());
    }

    public HospedeDTO add(HospedeDTO hospede) {
        HospedeEntity newE = new HospedeEntity();
        newE.setNome(hospede.getNome());
        newE.setDocumento(hospede.getDocumento());
        newE.setTelefone(hospede.getTelefone());
        newE = repository.save(newE);
        return new HospedeDTO(newE.getId(), newE.getNome(), newE.getDocumento(), newE.getTelefone());
    }

    public void delete(Long idHospede) {
        repository.findById(idHospede).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        repository.deleteById(idHospede);
    }

    public HospedeDTO getById(Long idHospede) {
        HospedeEntity e = repository.findById(idHospede).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new HospedeDTO(e.getId(), e.getNome(), e.getDocumento(), e.getTelefone());
    }

    public HospedeDTO update(Long idHospede, HospedeDTO hospede) {
        HospedeEntity e = repository.findById(idHospede).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (hospede.getNome() != null) {
            e.setNome(hospede.getNome());
        }
        if (hospede.getDocumento() != null) {
            e.setDocumento(hospede.getDocumento());
        }
        if (hospede.getTelefone() != null) {
            e.setTelefone(hospede.getTelefone());
        }
        e = repository.save(e);
        return new HospedeDTO(e.getId(), e.getNome(), e.getDocumento(), e.getTelefone());
    }

    public List<HospedeDTO> getByQuery(String termo) {
        return repository.findAllByNomeIgnoreCaseContainsOrDocumentoIgnoreCaseContainsOrTelefoneIgnoreCaseContains(termo, termo, termo).
                stream().map(h -> new HospedeDTO(h.getId(), h.getNome(), h.getDocumento(), h.getTelefone())).
                collect(Collectors.toList());
    }
}
