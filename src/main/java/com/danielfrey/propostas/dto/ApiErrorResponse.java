package com.danielfrey.propostas.dto;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {

    private final boolean sucesso = false; // Sempre falso em respostas de erro
    private final int status;
    private final String mensagem;
    private final Instant timestamp = Instant.now();
    private List<String> erros;

    // Construtor para erros genéricos (sem lista de erros de campo)
    public ApiErrorResponse(int status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
    }

    // Construtor para erros de validação (com lista de erros de campo)
    public ApiErrorResponse(int status, String mensagem, List<String> erros) {
        this.status = status;
        this.mensagem = mensagem;
        this.erros = erros;
    }
}