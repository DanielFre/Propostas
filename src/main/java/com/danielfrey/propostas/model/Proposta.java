package com.danielfrey.propostas.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_proposta")
@Getter
@Setter
@NoArgsConstructor
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 11)
    private String cpf;

    @Column(nullable = false)
    private BigDecimal valorSolicitado;

    @Column(nullable = false)
    private Integer quantidadeParcelas;

    @Column(nullable = false)
    private LocalDate dataSolicitacao;

    @OneToMany(mappedBy = "proposta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Parcela> parcelas = new ArrayList<>();

    public Proposta(String cpf, BigDecimal valorSolicitado, int quantidadeParcelas, LocalDate dataSolicitacao) {
        this.cpf = cpf;
        this.valorSolicitado = valorSolicitado;
        this.quantidadeParcelas = quantidadeParcelas;
        this.dataSolicitacao = dataSolicitacao;
    }
}
