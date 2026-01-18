package br.rosa.pagin.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import br.rosa.pagin.dto.AuthorDTO;
import br.rosa.pagin.util.PaginUtil;

@JsonTest
public class AuthorDTOJsonTest {

	@Autowired
	JacksonTester<AuthorDTO> authorJacksonTester;

	@Test
	void authorSerialization_Sucesso() throws IOException {
		Long id = 123L;
		String primeiro = "rod";
		String urtimo = "rosa";
		Date dataNasc = new Date();
		AuthorDTO author = new AuthorDTO(id, primeiro, urtimo, dataNasc);

		JsonContent<AuthorDTO> json = authorJacksonTester.write(author);

		assertThat(json).extractingJsonPathNumberValue("$.id").isEqualTo(id.intValue());
		assertThat(json).extractingJsonPathStringValue("$.firstName").isEqualTo(primeiro);
		assertThat(json).extractingJsonPathStringValue("$.lastName").isEqualTo(urtimo);
		assertThat(json).extractingJsonPathStringValue("$.birthday")
				.isEqualTo(PaginUtil.formataDataPadraoDDMMYYYY(dataNasc));
	}
}
