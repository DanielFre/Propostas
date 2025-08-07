package com.danielfrey.propostas.model.enums;

import lombok.Getter;

@Getter
public enum StatusParcela {

  EM_ABERTO(1, "Em Aberto"),
  PAGA(20, "Paga");

  private final int cod;
  private final String nome;

  private StatusParcela(int cod, String nome) {
    this.cod = cod;
    this.nome = nome;
  }

  public static StatusParcela toEnum(Integer cod) {
    if (cod == null) {
      return null;
    }

    for (StatusParcela x : StatusParcela.values()) {
      if (cod.equals(x.getCod())) {
        return x;
      }
    }

    throw new IllegalArgumentException("Código inválido: " + cod);
  }
}