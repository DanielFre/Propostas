package com.danielfrey.propostas.service.exception;

public class ParcelaNotFoundException extends RuntimeException {
    public ParcelaNotFoundException(String message) {
        super(message);
    }
}