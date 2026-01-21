package br.rosa.redis_ex.domain.converter;

import br.rosa.redis_ex.domain.enumerator.SexoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SexoConverter implements AttributeConverter<SexoEnum, String> {

    public String convertToDatabaseColumn(SexoEnum sexoEnum) {
        return sexoEnum != null ? sexoEnum.getCodigo() : null;
    }

    @Override
    public SexoEnum convertToEntityAttribute(String valor) {
        return SexoEnum.recuperarPeloCodigo(valor);
    }
}