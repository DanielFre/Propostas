package com.danielfrey.propostas.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPF, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.isEmpty()) {
            return true; // Considera nulo/vazio como válido, para usar @NotBlank separadamente
        }

        // Remove caracteres não numéricos
        String cpfNumerico = cpf.replaceAll("\\D", "");

        // Verifica se tem 11 dígitos
        if (cpfNumerico.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (ex: 111.111.111-11)
        if (cpfNumerico.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            // Cálculo do primeiro dígito verificador
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += (10 - i) * (cpfNumerico.charAt(i) - '0');
            }
            int digito1 = 11 - (soma % 11);
            if (digito1 > 9) {
                digito1 = 0;
            }

            // Verifica o primeiro dígito
            if ((cpfNumerico.charAt(9) - '0') != digito1) {
                return false;
            }

            // Cálculo do segundo dígito verificador
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += (11 - i) * (cpfNumerico.charAt(i) - '0');
            }
            int digito2 = 11 - (soma % 11);
            if (digito2 > 9) {
                digito2 = 0;
            }

            // Verifica o segundo dígito
            return (cpfNumerico.charAt(10) - '0') == digito2;

        } catch (Exception e) {
            return false;
        }
    }
}