package com.danielfrey.propostas.dto;

import lombok.Getter;

@Getter
public class PropostaResponse {

    private final boolean sucesso = true; // Sempre verdadeiro em respostas de sucesso
    private final Long id;
    private final String mensagem;

    public PropostaResponse(Long id, String mensagem) {
        this.id = id;
        this.mensagem = mensagem;
    }
}