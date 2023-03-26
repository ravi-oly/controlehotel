package com.ravioly.controlehotel.repository;

import com.ravioly.controlehotel.entity.HospedeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospedeRepository extends JpaRepository<HospedeEntity, Long> {
    List<HospedeEntity> findAllByNomeIgnoreCaseContainsOrDocumentoIgnoreCaseContainsOrTelefoneIgnoreCaseContains(String nome, String documento, String telefone);
    List<HospedeEntity> findAllByRegistros_DataSaidaNull();
    @Query(value = "select distinct h.* \n" +
            "from hospede h, registro r \n" +
            "where r.id_hospede = h.id \n" +
            "and r.checkout is not null \n" +
            "and not exists (select 1 from registro a where a.checkin > r.checkout and a.checkout is null);", nativeQuery = true)
    List<HospedeEntity> findAllByRegistros_DataSaidaNotNull();
}
