package com.danielfrey.propostas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParcelaResponse {

    private Integer numeroParcela;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private String status;

}