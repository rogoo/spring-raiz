package br.rosa.pagin.controller.base;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.rosa.pagin.dto.AuthorDTO;
import br.rosa.pagin.dto.response.RespostaDTO;
import br.rosa.pagin.model.Author;

public class BaseTest {

	/**
	 * Converte o objeto para uma linda string no formato JSON.
	 * 
	 * @param obj
	 * @return
	 */
	public static String convertJson(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public RespostaDTO<List<AuthorDTO>> criarRespostaDTOAuthor(int retornoHttp, int totalRegistros) {
		RespostaDTO<List<AuthorDTO>> retorno = new RespostaDTO<List<AuthorDTO>>();
		List<AuthorDTO> listaAuthor = new ArrayList<AuthorDTO>();

		Calendar c = Calendar.getInstance();

		for (int i = 1; i <= totalRegistros; i++) {
			listaAuthor.add(new AuthorDTO((long) i, "Primeiro nome " + i, "Ultimo " + i, c.getTime()));
			c.add(Calendar.DAY_OF_MONTH, 1);
		}

		retorno.setRetorno(listaAuthor);
		retorno.setCodigo(retornoHttp);

		return retorno;
	}

	public Page<Author> criarPageListAuthor(int retornoHttp, int totalRegistrosLista, Pageable pageable,
			long totalRegistrosGeral) {
		return new PageImpl<Author>(criarListAuthor(totalRegistrosLista), pageable, totalRegistrosGeral);
	}

	public List<Author> criarListAuthor(int totalRegistrosLista) {
		List<Author> listaAuthor = new ArrayList<Author>();

		Calendar c = Calendar.getInstance();

		for (int i = 1; i <= totalRegistrosLista; i++) {
			listaAuthor.add(new Author((long) i, "Primeiro nome " + i, "Ultimo " + i, c.getTime()));
			c.add(Calendar.DAY_OF_MONTH, 1);
		}

		return listaAuthor;
	}

	public RespostaDTO<AuthorDTO> criarRespostaDTOAuthor(int retornoHttp, Long id, String primeiroNome,
			String urtimoNome, Date dataNasc) {
		RespostaDTO<AuthorDTO> retorno = new RespostaDTO<AuthorDTO>();

		retorno.setRetorno(new AuthorDTO(id, primeiroNome, urtimoNome, dataNasc));
		retorno.setCodigo(retornoHttp);

		return retorno;
	}
}
