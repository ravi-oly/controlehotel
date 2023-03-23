package com.ravioly.controlehotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HospedeDTO {
    private Long id;
    private String nome;
    private String documento;
    private String telefone;
}
