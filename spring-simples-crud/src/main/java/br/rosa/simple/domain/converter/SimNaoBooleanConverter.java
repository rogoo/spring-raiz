package br.rosa.simple.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SimNaoBooleanConverter implements AttributeConverter<Boolean, String> {

	private final String CARACTER_S = "S";
	private final String CARACTER_N = "N";

	@Override
	public String convertToDatabaseColumn(Boolean valor) {
		return Boolean.TRUE.equals(valor) ? CARACTER_S : CARACTER_N;
	}

	@Override
	public Boolean convertToEntityAttribute(String valor) {
		return CARACTER_S.equals(valor);
	}
}
