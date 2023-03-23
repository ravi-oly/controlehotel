package com.ravioly.controlehotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HospedeRegistroDTO {
    private String nome;
    private String documento;
    private Double valorTotalGasto;
    private Double valorUltimaHospedagem;
}
