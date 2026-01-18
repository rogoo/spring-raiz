package br.rosa.pagin.dto;

import java.util.Date;

public record PostDTO(Long id, String title, String description, Date timeCreated, Date timeDisabled, Long idAuthor) {

	public static final String NOME_DTO = "Post";
	public static final String CAMPO_TITLE = "title";
	public static final String CAMPO_DESCRIPTION = "description";
}
