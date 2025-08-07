package com.danielfrey.propostas.service.exception;

public class PropostaNotFoundException extends RuntimeException {
    public PropostaNotFoundException(String message) {
        super(message);
    }
}