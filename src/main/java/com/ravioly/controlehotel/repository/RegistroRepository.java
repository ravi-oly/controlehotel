package com.ravioly.controlehotel.repository;

import com.ravioly.controlehotel.entity.RegistroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroRepository extends JpaRepository<RegistroEntity, Long> {

    List<RegistroEntity> findAllByHospede_IdAndDataSaidaNotNullOrderByDataEntradaDesc(Long idHospede);

}
