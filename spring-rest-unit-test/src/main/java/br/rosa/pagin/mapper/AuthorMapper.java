package br.rosa.pagin.mapper;

import java.util.ArrayList;
import java.util.List;

import br.rosa.pagin.dto.AuthorDTO;
import br.rosa.pagin.model.Author;

public class AuthorMapper {

	private AuthorMapper() {
		super();
	}

	public static AuthorDTO converteModelToDTO(Author author) {
		return new AuthorDTO(author.getId(), author.getFirstName(), author.getLastName(), author.getBirthday());
	}

	public static List<AuthorDTO> converteModelToDTO(List<Author> listaAuthor) {
		List<AuthorDTO> listaRetorno = new ArrayList<AuthorDTO>();

		for (Author author : listaAuthor) {
			listaRetorno.add(converteModelToDTO(author));
		}

		return listaRetorno;
	}
}
