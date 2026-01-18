package br.rosa.pagin.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public record AuthorDTO(Long id, String firstName, String lastName,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") Date birthday) {

	public static String modelAuthor() {
		return "Author";
	}

	public static String campoFirstName() {
		return "firstName";
	}

	public static String campoLastName() {
		return "lastName";
	}

	public static String campoBirthday() {
		return "birthday";
	}
}
