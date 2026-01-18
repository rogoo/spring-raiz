package br.rosa.simple.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -1018355024036251860L;

	public BadRequestException() {
		// comentario teste
		super();
	}

	public BadRequestException(String message) {
		super(message);
	}
}
