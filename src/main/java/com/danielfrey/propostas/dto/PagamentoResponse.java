package com.danielfrey.propostas.dto;

import lombok.Getter;

@Getter
public class PagamentoResponse {

    private final boolean sucesso = true;
    private final String mensagem;

    public PagamentoResponse(String mensagem) {
        this.mensagem = mensagem;
    }
}