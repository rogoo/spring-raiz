package br.rosa.pagin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import br.rosa.pagin.controller.base.BaseTest;
import br.rosa.pagin.dto.AuthorDTO;
import br.rosa.pagin.dto.PostDTO;
import br.rosa.pagin.util.PaginMensagemUtil;
import br.rosa.pagin.util.PaginUtil;
import br.rosa.pagin.util.constantes.PaginMensagensConstantes;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/sql-test/init-db-test.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
public class AuthorControllerSpringBootTest extends BaseTest {

	public static final String URL_PADRAO = "/v1/authors";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void listaAuthor_SimplesChamada_Sucesso() throws Exception {
		ResultActions result = mockMvc.perform(get(URL_PADRAO));

		// @formatter:off
		result.andExpect(status().isOk())
			  .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			  .andExpect(jsonPath("$.codigo").value(200))
			  .andExpect(jsonPath("$.retorno").exists())
			  .andExpect(jsonPath("$.listaErro").doesNotExist())
			  .andExpect(jsonPath("$.totalRegistros").exists());
		// @formatter:on
	}

	@Test
	void listaAuthor_PaginacaoMaiorQue100_Erro() throws Exception {
		// @formatter:off
		mockMvc.perform(get(URL_PADRAO).param("size", "101"))
		       .andExpect(status().isBadRequest())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		       .andExpect(jsonPath("$.codigo").value(400))
		       .andExpect(jsonPath("$.totalRegistros").value(0))
		       .andExpect(jsonPath("$.listaErro.length()").value(1))
		       .andExpect(jsonPath("$.listaErro[:1].message").value(PaginMensagensConstantes.MSG_LIMITE_RETORNO_REGISTROS));
		// @formatter:on
	}

	@Test
	void listaAuthor_PaginacaoIgual100_Sucesso() throws Exception {
		// @formatter:off
		mockMvc.perform(get(URL_PADRAO).param("size", "100"))
		        .andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.codigo").value(200))
			    .andExpect(jsonPath("$.retorno").exists())
				.andExpect(jsonPath("$.listaErro").doesNotExist())
				.andExpect(jsonPath("$.totalRegistros").exists());
		// @formatter:on
	}

	@Test
	void listaAuthor_PaginacaoIgualDois_Sucesso() throws Exception {
		// @formatter:off
		mockMvc.perform(get(URL_PADRAO).param("size", "2"))
		        .andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.codigo").value(200))
			    .andExpect(jsonPath("$.retorno").exists())
				.andExpect(jsonPath("$.listaErro").doesNotExist())
				.andExpect(jsonPath("$.totalRegistros").exists())
				.andExpect(jsonPath("$.retorno.length()").value(2));
		// @formatter:on
	}

	@Test
	void pesquisarPorId_Sucesso() throws Exception {
		// @formatter:off
		mockMvc.perform(get(URL_PADRAO + "/{id}", 2))
		       .andExpect(status().isOk())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			   .andExpect(jsonPath("$.codigo").value(200))
			   .andExpect(jsonPath("$.retorno.id").value(2))
			   .andExpect(jsonPath("$.retorno.firstName").value("Ana"))
			   .andExpect(jsonPath("$.retorno.lastName").value("Test 2"))
			   .andExpect(jsonPath("$.retorno.birthday").value("25/07/2000"))
			   .andExpect(jsonPath("$.totalRegistros").value(0));
		// @formatter:off
	}

	@Test
	void pesquisarPorId_IdentificadorNaoExistente() throws Exception {
		// @formatter:off
		mockMvc.perform(get(URL_PADRAO + "/{id}", 555))
		       .andExpect(status().isNotFound())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			   .andExpect(jsonPath("$.codigo").value(404))
			   .andExpect(jsonPath("$.retorno").doesNotExist())
			   .andExpect(jsonPath("$.totalRegistros").value(0));
		// @formatter:off
	}

	@Test
	void pesquisarPostsDoAuthor_Sucesso() throws Exception {
		// @formatter:off
		mockMvc.perform(get(URL_PADRAO + "/{id}/posts", 1))
		       .andExpect(status().isOk())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			   .andExpect(jsonPath("$.codigo").value(200))
			   .andExpect(jsonPath("$.retorno.length()").value(3))
			   .andExpect(jsonPath("$.totalRegistros").value(3));
		// @formatter:off
	}

	@Test
	void pesquisarPostsDoAuthor_AuthorNaoPossuiPosts() throws Exception {
		// @formatter:off
		mockMvc.perform(get(URL_PADRAO + "/{id}/posts", 11))
		       .andExpect(status().isOk())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			   .andExpect(jsonPath("$.codigo").value(200))
			   .andExpect(jsonPath("$.retorno.length()").value(0))
			   .andExpect(jsonPath("$.totalRegistros").value(0));
		// @formatter:off
	}

	@Test
	void pesquisarPostsDoAuthor_NaoExisteAuthorInformado() throws Exception {
		// @formatter:off
		mockMvc.perform(get(URL_PADRAO + "/{id}/posts", 111))
		       .andExpect(status().isBadRequest())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			   .andExpect(jsonPath("$.codigo").value(400))
			   .andExpect(jsonPath("$.retorno").doesNotExist())
			   .andExpect(jsonPath("$.listaErro[:1].message").value(PaginMensagemUtil
						.criarMensagem(PaginMensagensConstantes.MSG_NAO_EXISTE_COM_IDENTIFICADOR,
								"Author", 111)))
			   .andExpect(jsonPath("$.totalRegistros").value(0));
		// @formatter:off
	}

	@Test
	void novoAuthor_Sucesso() throws Exception {
		AuthorDTO authorDTO = new AuthorDTO(7L, "Rod Novo", "Rosa Novo", new Date());

		// @formatter:off
		mockMvc.perform(post(URL_PADRAO)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertJson(authorDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.codigo").value(201));
		// @formatter:on
	}

	@Test
	void novoAuthor_IdNull_Sucesso() throws Exception {
		AuthorDTO authorDTO = new AuthorDTO(null, "Rod Novo id null", "Rosa Novo id null", new Date());

		// @formatter:off
		mockMvc.perform(post(URL_PADRAO)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertJson(authorDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.codigo").value(201));
		// @formatter:on
	}

	@Test
	void novoAuthor_SemPrimeiroNome_Erro() throws Exception {
		AuthorDTO authorDTO = new AuthorDTO(null, null, "Urtimo Nome", new Date());

		// @formatter:off
		mockMvc.perform(post(URL_PADRAO)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertJson(authorDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.codigo").value(400))
				.andExpect(jsonPath("$.listaErro[:1].message").value(
						PaginMensagemUtil.criarMensagemCampoObrigatorio(AuthorDTO.campoFirstName())));
		// @formatter:on
	}

	@Test
	void novoAuthor_SemUrtimoNome_Erro() throws Exception {
		AuthorDTO authorDTO = new AuthorDTO(null, "Ridrigo", null, new Date());

		// @formatter:off
		mockMvc.perform(post(URL_PADRAO)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertJson(authorDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.codigo").value(400))
				.andExpect(jsonPath("$.listaErro[:1].message").value(
						PaginMensagemUtil.criarMensagemCampoObrigatorio(AuthorDTO.campoLastName())));
		// @formatter:on
	}

	@Test
	void novoAuthor_SemDatinhaDeNascimento_Erro() throws Exception {
		AuthorDTO authorDTO = new AuthorDTO(null, "Ridrigo", "Urtimo", null);

		// @formatter:off
		mockMvc.perform(post(URL_PADRAO)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertJson(authorDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.codigo").value(400))
				.andExpect(jsonPath("$.listaErro[:1].message").value(
						PaginMensagemUtil.criarMensagemCampoObrigatorio(AuthorDTO.campoBirthday())));
		// @formatter:on
	}

	@Test
	void novoAuthor_SemNenhumCampo_Erro() throws Exception {
		AuthorDTO authorDTO = new AuthorDTO(null, null, null, null);

		// @formatter:off
		mockMvc.perform(post(URL_PADRAO)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertJson(authorDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.codigo").value(400))
				.andExpect(jsonPath("$.listaErro.length()").value(3));
		// @formatter:on
	}

	@Test
	void deletar_Sucesso() throws Exception {
		// @formatter:off
		mockMvc.perform(delete(URL_PADRAO + "/{id}", 11)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.codigo").value(200));
		// @formatter:on
	}

	@Test
	void deletar_NaoExisteAuthor_Erro() throws Exception {
		// @formatter:off
		mockMvc.perform(delete(URL_PADRAO + "/{id}", 555)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.codigo").value(400))
		.andExpect(jsonPath("$.listaErro[:1].message").value(PaginMensagemUtil.criarMensagem(
				PaginMensagensConstantes.MSG_NAO_EXISTE_COM_IDENTIFICADOR, AuthorDTO.modelAuthor(), 555)));
		// @formatter:on
	}

	@Test
	void deletar_ExisteDependenciaPost_Erro() throws Exception {
		// @formatter:off
		mockMvc.perform(delete(URL_PADRAO + "/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.codigo").value(400))
				.andExpect(jsonPath("$.listaErro[:1].message").value(
						PaginMensagemUtil.criarMensagem(PaginMensagensConstantes.MSG_DELECAO_EXISTE_VINCULACAO,
								AuthorDTO.modelAuthor(), 1, PostDTO.NOME_DTO)));
		// @formatter:on
	}

	@Test
	void atualizarParcial_NenhumCampoInformado() throws Exception {
		AuthorDTO authorDTO = new AuthorDTO(null, null, null, null);

		// @formatter:off
		mockMvc.perform(patch(URL_PADRAO+"/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertJson(authorDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.codigo").value(400))
				.andExpect(jsonPath("$.listaErro[:1].message").value(
						PaginMensagensConstantes.MSG_SEM_ALTERACAO));
		// @formatter:on
	}

	@Test
	void atualizarParcial_NaoExisteAuthor() throws Exception {
		AuthorDTO authorDTO = new AuthorDTO(null, null, null, null);

		// @formatter:off
		mockMvc.perform(patch(URL_PADRAO+"/{id}", 666)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertJson(authorDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.codigo").value(400))
				.andExpect(jsonPath("$.listaErro[:1].message").value(PaginMensagemUtil.criarMensagem(
						PaginMensagensConstantes.MSG_NAO_EXISTE_COM_IDENTIFICADOR,
						AuthorDTO.modelAuthor(), 666)));
		// @formatter:on
	}

	@Test
	void atualizarParcial_AlterarPrimeiroNome() throws Exception {
		String primeiro = "Rod Alteradoo 2";
		AuthorDTO authorDTO = new AuthorDTO(null, primeiro, null, null);

		// @formatter:off
		mockMvc.perform(patch(URL_PADRAO+"/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertJson(authorDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.codigo").value(200))
				.andExpect(jsonPath("$.retorno.id").value(1))
				.andExpect(jsonPath("$.retorno.firstName").value(primeiro));
		// @formatter:on
	}

	@Test
	void atualizarParcial_AlterarSegundoNome() throws Exception {
		String segundoNome = "Last Alteradoo 2";
		AuthorDTO authorDTO = new AuthorDTO(null, null, segundoNome, null);

		// @formatter:off
		mockMvc.perform(patch(URL_PADRAO+"/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertJson(authorDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.codigo").value(200))
				.andExpect(jsonPath("$.retorno.id").value(1))
				.andExpect(jsonPath("$.retorno.lastName").value(segundoNome));
		// @formatter:on
	}

	@Test
	void atualizarParcial_AlterarDatinhaNascimento() throws Exception {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2005);
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DAY_OF_MONTH, 25);

		AuthorDTO authorDTO = new AuthorDTO(null, null, null, c.getTime());

		// @formatter:off
		mockMvc.perform(patch(URL_PADRAO+"/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertJson(authorDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.codigo").value(200))
				.andExpect(jsonPath("$.retorno.id").value(1))
				.andExpect(jsonPath("$.retorno.birthday")
						.value(PaginUtil.formataDataPadraoDDMMYYYY(c.getTime())));
		// @formatter:on
	}

	@Test
	void atualizarParcial_AlterarTodosCampos() throws Exception {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2145);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.DAY_OF_MONTH, 25);

		AuthorDTO authorDTO = new AuthorDTO(null, "Nica", "Lindão", c.getTime());

		// @formatter:off
		mockMvc.perform(patch(URL_PADRAO+"/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertJson(authorDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.codigo").value(200))
				.andExpect(jsonPath("$.retorno.id").value(1))
				.andExpect(jsonPath("$.retorno.firstName").value("Nica"))
				.andExpect(jsonPath("$.retorno.lastName").value("Lindão"))
				.andExpect(jsonPath("$.retorno.birthday")
						.value(PaginUtil.formataDataPadraoDDMMYYYY(c.getTime())));
		// @formatter:on
	}

	@Test
	void atualizarTudo_AuthorNaoExiste() throws Exception {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2145);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.DAY_OF_MONTH, 25);

		AuthorDTO authorDTO = new AuthorDTO(null, "Nica", "Lindão", c.getTime());

		// @formatter:off
		mockMvc.perform(patch(URL_PADRAO+"/{id}", 999)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertJson(authorDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.codigo").value(400))
				.andExpect(jsonPath("$.listaErro[:1].message").value(PaginMensagemUtil.criarMensagem(
						PaginMensagensConstantes.MSG_NAO_EXISTE_COM_IDENTIFICADOR,
						AuthorDTO.modelAuthor(), 999)));;
		// @formatter:on
	}

	@Test
	void atualizarTudo_FaltouPrimeiroNome() throws Exception {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2145);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.DAY_OF_MONTH, 25);

		AuthorDTO authorDTO = new AuthorDTO(null, null, "Lindão", c.getTime());

		// @formatter:off
		mockMvc.perform(put(URL_PADRAO+"/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertJson(authorDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.codigo").value(400))
				.andExpect(jsonPath("$.listaErro[:1].message")
						.value(PaginMensagensConstantes.MSG_PREENCHA_TODOS_CAMPOS));
		// @formatter:on
	}

	@Test
	void atualizarTudo_FaltouSegundoNome() throws Exception {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2145);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.DAY_OF_MONTH, 25);

		AuthorDTO authorDTO = new AuthorDTO(null, "Sofia", null, c.getTime());

		// @formatter:off
		mockMvc.perform(put(URL_PADRAO+"/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertJson(authorDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.codigo").value(400))
				.andExpect(jsonPath("$.listaErro[:1].message")
						.value(PaginMensagensConstantes.MSG_PREENCHA_TODOS_CAMPOS));
		// @formatter:on
	}

	@Test
	void atualizarTudo_FaltouDataNascimento() throws Exception {
		AuthorDTO authorDTO = new AuthorDTO(null, "Sofia", "Linda", null);

		// @formatter:off
		mockMvc.perform(put(URL_PADRAO+"/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertJson(authorDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.codigo").value(400))
				.andExpect(jsonPath("$.listaErro[:1].message")
						.value(PaginMensagensConstantes.MSG_PREENCHA_TODOS_CAMPOS));
		// @formatter:on
	}

	@Test
	void atualizarTudo_Sucesso() throws Exception {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 6767);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 23);

		String primeiroNome = "Nica 71";
		String urtimoNome = "Nica 71";
		Date dataNascimento = c.getTime();
		String dataNascimentoString = PaginUtil.formataDataPadraoDDMMYYYY(dataNascimento);

		AuthorDTO authorDTO = new AuthorDTO(null, primeiroNome, urtimoNome, dataNascimento);

		// @formatter:off
		mockMvc.perform(patch(URL_PADRAO+"/{id}", 4)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(convertJson(authorDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.codigo").value(200))
				.andExpect(jsonPath("$.retorno.id").value(4))
				.andExpect(jsonPath("$.retorno.firstName").value(primeiroNome))
				.andExpect(jsonPath("$.retorno.lastName").value(urtimoNome))
				.andExpect(jsonPath("$.retorno.birthday").value(dataNascimentoString));
		// @formatter:on
	}
}
