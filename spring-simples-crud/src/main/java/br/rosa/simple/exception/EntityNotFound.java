package br.rosa.simple.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EntityNotFound extends RuntimeException {

	private static final long serialVersionUID = -1018355024036251860L;

	public EntityNotFound() {
		super();
	}

	public EntityNotFound(String message) {
		super(message);
	}
}
