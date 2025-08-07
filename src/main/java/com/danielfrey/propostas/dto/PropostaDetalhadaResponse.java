package com.danielfrey.propostas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropostaDetalhadaResponse {

    private Long id;
    private String cpf;
    private BigDecimal valorSolicitado;
    private Integer quantidadeParcelas;
    private LocalDate dataSolicitacao;
    private List<ParcelaResponse> parcelas;

}