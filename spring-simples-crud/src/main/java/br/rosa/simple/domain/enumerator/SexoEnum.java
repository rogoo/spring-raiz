package br.rosa.simple.domain.enumerator;

import java.util.stream.Stream;

public enum SexoEnum {

	MASCULINO("M", "Masculino"), FEMININO("F", "Feminino"), OUTRO("O", "Outro");

	private String codigo;
	private String descricao;

	private SexoEnum(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static SexoEnum recuperarPeloCodigo(String valor) {
		return Stream.of(SexoEnum.values())
				.filter(e -> e.getCodigo().equalsIgnoreCase(valor)).findAny()
				.orElse(null);
	}
}
