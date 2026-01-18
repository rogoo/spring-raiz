package br.rosa.pagin.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 941615187207474521L;

	public NotFoundException(Long id) {
		super("Cound not found with id: " + id);
	}
}
