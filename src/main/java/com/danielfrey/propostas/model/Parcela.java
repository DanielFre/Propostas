package com.danielfrey.propostas.model;

import com.danielfrey.propostas.model.enums.StatusParcela;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa uma parcela de uma proposta de crédito.
 */
@Entity
@Table(name = "tb_parcela")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Relacionamento Muitos-para-Um com Proposta.
     * FetchType.LAZY significa que a Proposta só será carregada do banco quando for explicitamente acessada.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proposta_id", nullable = false)
    private Proposta proposta;

    @Column(nullable = false)
    private Integer numeroParcela;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDate dataVencimento;

    /**
     * O status da parcela.
     * A conversão para o valor do banco (Integer) é feita pelo StatusParcelaConverter.
     */
    private StatusParcela status;
}