package com.danielfrey.propostas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danielfrey.propostas.model.Proposta;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {
}