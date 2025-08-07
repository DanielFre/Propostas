package com.danielfrey.propostas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danielfrey.propostas.model.Parcela;

@Repository
public interface ParcelaRepository extends JpaRepository<Parcela, Long> {

    /**
     * Busca uma parcela específica pelo ID da proposta e pelo número.
     * O Spring Data JPA cria a query automaticamente a partir do nome do método.
     */
    Optional<Parcela> findByPropostaIdAndNumeroParcela(Long propostaId, Integer numeroParcela);
}