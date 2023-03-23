package com.ravioly.controlehotel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "hospede")
public class HospedeEntity {

    @Column(name = "id", columnDefinition = "bigint")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", columnDefinition = "varchar(100)", nullable = false)
    private String nome;

    @Column(name = "documento", columnDefinition = "varchar(50)", nullable = false)
    private String documento;

    @Column(name = "telefone", columnDefinition = "varchar(50)", nullable = false)
    private String telefone;

    @Column(name = "total_gasto", columnDefinition = "numeric(12,2)")
    private Double totalGasto;

    @OneToMany(mappedBy = "hospede")
    private List<RegistroEntity> registros;
}
