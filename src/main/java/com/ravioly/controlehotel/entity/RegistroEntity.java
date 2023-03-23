package com.ravioly.controlehotel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "registro")
public class RegistroEntity {

    @Column(name = "id", columnDefinition = "bigint")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hospede", referencedColumnName = "id")
    private HospedeEntity hospede;

    @Column(name = "checkin", columnDefinition = "timestamp", nullable = false)
    private LocalDateTime dataEntrada;

    @Column(name = "checkout", columnDefinition = "timestamp")
    private LocalDateTime dataSaida;

    @Column(name = "adicional_veiculo", columnDefinition = "boolean", nullable = false)
    private Boolean adicionalVeiculo;

    @Column(name = "valor_total", columnDefinition = "numeric(12,2)")
    private Double valorTotal;
}
