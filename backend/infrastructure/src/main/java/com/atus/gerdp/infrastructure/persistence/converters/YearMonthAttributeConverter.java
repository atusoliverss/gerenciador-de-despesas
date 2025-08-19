package com.atus.gerdp.infrastructure.persistence.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.YearMonth;
import java.util.Optional;

/**
 * Converte o tipo YearMonth (Java) para String (banco de dados) e vice-versa.
 * O JPA não sabe como salvar YearMonth por padrão, então ensinamos a ele.
 * @Converter(autoApply = true) faz com que essa conversão seja usada em toda a aplicação.
 */
@Converter(autoApply = true)
public class YearMonthAttributeConverter implements AttributeConverter<YearMonth, String> {

    /**
     * Converte de YearMonth (ex: 2025-08) para uma String "2025-08" para salvar no banco.
     */
    @Override
    public String convertToDatabaseColumn(YearMonth attribute) {
        return Optional.ofNullable(attribute).map(YearMonth::toString).orElse(null);
    }

    /**
     * Converte da String "2025-08" do banco de volta para um objeto YearMonth no Java.
     */
    @Override
    public YearMonth convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData).map(YearMonth::parse).orElse(null);
    }
}