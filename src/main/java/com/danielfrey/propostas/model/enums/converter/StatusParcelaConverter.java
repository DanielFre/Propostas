package com.danielfrey.propostas.model.enums.converter;

import com.danielfrey.propostas.model.enums.StatusParcela;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusParcelaConverter implements AttributeConverter<StatusParcela, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusParcela statusParcela) {
        if (statusParcela == null) {
            return null;
        }
        return statusParcela.getCod();
    }

    @Override
    public StatusParcela convertToEntityAttribute(Integer cod) {
        if (cod == null) {
            return null;
        }
        return StatusParcela.toEnum(cod);
    }
}