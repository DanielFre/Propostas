package com.danielfrey.propostas.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CPFValidator.class) // Link para a classe que contém a lógica
@Target({ElementType.FIELD}) // A anotação só pode ser usada em campos
@Retention(RetentionPolicy.RUNTIME) // A anotação precisa estar disponível em tempo de execução
public @interface CPF {

    // Mensagem de erro padrão caso a validação falhe
    String message() default "CPF inválido";

    // Padrão da especificação Bean Validation (obrigatório)
    Class<?>[] groups() default {};

    // Padrão da especificação Bean Validation (obrigatório)
    Class<? extends Payload>[] payload() default {};
}