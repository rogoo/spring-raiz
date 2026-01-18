package br.rosa.pagin.dto.response;

public class ErroDTO {

	private String message;

	public ErroDTO() {
		super();
	}

	public ErroDTO(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
