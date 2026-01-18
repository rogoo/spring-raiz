package br.rosa.pagin.controller.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import br.rosa.pagin.dto.response.RespostaAbstractDTO;

public class BaseController {

	/**
	 * Realiza validação de erros, jogando exceção em caso afirmativo.
	 * 
	 * @param mensagemErros
	 */
	public void realizaValidacao(String mensagemErros) {
		if (mensagemErros != null && mensagemErros.length() != 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mensagemErros);
		}
	}

	/**
	 * Valida o retorno, fazendo as covnersões.
	 * 
	 * @param resposta
	 * @return
	 */
	public <T extends RespostaAbstractDTO<?>> ResponseEntity<T> validaRetorno(T resposta) {
		if (resposta == null) {
			return null;
		}

		return ResponseEntity.status(resposta.getCodigo()).body(resposta);
	}
}
