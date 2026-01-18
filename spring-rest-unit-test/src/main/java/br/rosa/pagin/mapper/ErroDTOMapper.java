package br.rosa.pagin.mapper;

import java.util.ArrayList;
import java.util.List;

import br.rosa.pagin.dto.response.ErroDTO;

public class ErroDTOMapper {

	private ErroDTOMapper() {
		super();
	}

	public static ErroDTO converte(String mensagem) {
		return new ErroDTO(mensagem);
	}

	public static List<ErroDTO> converte(List<String> ListaMensagem) {
		List<ErroDTO> listaRetorno = new ArrayList<ErroDTO>();

		for (String msg : ListaMensagem) {
			listaRetorno.add(converte(msg));
		}

		return listaRetorno;
	}
}
