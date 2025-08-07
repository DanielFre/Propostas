package com.danielfrey.propostas.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.danielfrey.propostas.validation.CPF;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
public class PropostaRequest {

    @NotBlank(message = "O CPF não pode estar em branco.")
    @CPF
    private String cpf;

    @Setter
    @NotNull(message = "O valor solicitado não pode ser nulo.")
    @DecimalMin(value = "100.00", message = "O valor solicitado deve ser de no mínimo R$ 100,00.")
    private BigDecimal valorSolicitado;

    @Setter
    @NotNull(message = "A quantidade de parcelas não pode ser nula.")
    @Min(value = 1, message = "A quantidade de parcelas deve ser no mínimo 1.")
    @Max(value = 24, message = "A quantidade de parcelas deve ser no máximo 24.")
    private Integer quantidadeParcelas;

    @Setter
    private LocalDate dataSolicitacao = LocalDate.now();

    /**
     * Setter customizado para o campo CPF.
     * Remove todos os caracteres não numéricos antes de atribuir o valor ao campo
     * 'cpf'.
     *
     * @param cpf O CPF vindo da requisição, que pode estar formatado.
     */
    public void setCpf(String cpf) {
        if (cpf != null) {
            this.cpf = cpf.replaceAll("\\D", "");
        } else {
            this.cpf = null;
        }
    }
}