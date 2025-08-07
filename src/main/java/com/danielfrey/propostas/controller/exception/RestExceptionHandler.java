package com.danielfrey.propostas.controller.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.danielfrey.propostas.dto.ApiErrorResponse;
import com.danielfrey.propostas.service.exception.ParcelaJaPagaException;
import com.danielfrey.propostas.service.exception.ParcelaNotFoundException;
import com.danielfrey.propostas.service.exception.PropostaNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {
    // Para registrar os erros no console do servidor
    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> mensagensDeErro = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        return new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação. Verifique os campos com erro.",
                mensagensDeErro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "JSON malformado ou com tipo de dado inválido. Verifique o corpo da requisição.");
    }

    @ExceptionHandler(ParcelaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleParcelaNotFoundException(ParcelaNotFoundException ex) {
        return new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(ParcelaJaPagaException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleParcelaJaPagaException(ParcelaJaPagaException ex) {
        return new ApiErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(PropostaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handlePropostaNotFoundException(PropostaNotFoundException ex) {
        return new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNoResourceFoundException(NoResourceFoundException ex) {
        return new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado. Verifique a URL da requisição.");
    }

    /**
     * Manipulador de exceções genérico.
     * Captura qualquer exceção não tratada especificamente pelos outros handlers.
     * Retorna um status 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleGenericException(Exception ex) {
        // 1. REGISTRA O ERRO NO LOG DO SERVIDOR
        log.error("Ocorreu um erro inesperado na aplicação", ex);

        // 2. RETORNA UMA MENSAGEM GENÉRICA PARA O CLIENTE
        return new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado no servidor. Por favor, verifique os dados e parâmetros enviados e tente novamente mais tarde.");
    }
}