package br.rosa.pagin.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.rosa.pagin.controller.base.BaseTest;
import br.rosa.pagin.dto.response.RespostaDTO;
import br.rosa.pagin.service.AuthorService;
import br.rosa.pagin.service.PostService;
import br.rosa.pagin.util.PaginUtil;
import br.rosa.pagin.util.constantes.PaginMensagensConstantes;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerApenasJunitTest extends BaseTest {

	public static final String URL_PADRAO = "/v1/authors";

	@InjectMocks
	AuthorController authorController;

	@Mock
	AuthorService authorService;

	@Mock
	PostService postService;

	MockMvc mockMvc;

	@BeforeEach
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(authorController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
	}

	@Test
	void listaAuthor_SimplesChamada_Sucesso() throws Exception {
		when(this.authorService.listaAuthor(any())).thenReturn(criarRespostaDTOAuthor(200, 10));

		ResultActions result = mockMvc.perform(get(URL_PADRAO));

		// result.andDo(print());

		// @formatter:off
		result.andExpect(status().isOk())
			  .andExpect(jsonPath("$.codigo").value(200))
			  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
			  .andExpect(jsonPath("$.retorno").exists())
			  .andExpect(jsonPath("$.listaErro").doesNotExist())
			  .andExpect(jsonPath("$.totalRegistros").exists());
		// @formatter:on
	}

	@Test
	void listaAuthor_PaginacaoMaiorQue100_Erro() throws Exception {
		when(this.authorService.listaAuthor(any()))
				.thenReturn(RespostaDTO.criarRespostaErro(PaginMensagensConstantes.MSG_LIMITE_RETORNO_REGISTROS));

		// @formatter:off
		mockMvc.perform(get(URL_PADRAO).param("size", "101"))
		       .andExpect(status().isBadRequest())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		       .andExpect(jsonPath("$.codigo").value(400))
		       .andExpect(jsonPath("$.listaErro.length()").value(1))
		       .andExpect(jsonPath("$.listaErro[:1].message").value(PaginMensagensConstantes.MSG_LIMITE_RETORNO_REGISTROS));
		// @formatter:on
	}

	@Test
	void listaAuthor_PaginacaoIgual100_Sucesso() throws Exception {
		when(this.authorService.listaAuthor(any())).thenReturn(criarRespostaDTOAuthor(200, 100));

		// @formatter:off
		mockMvc.perform(get(URL_PADRAO).param("size", "100"))
		        .andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.codigo").value(200))
			    .andExpect(jsonPath("$.retorno").exists())
			    .andExpect(jsonPath("$.retorno.length()").value(100))
				.andExpect(jsonPath("$.listaErro").doesNotExist())
				.andExpect(jsonPath("$.totalRegistros").exists());
		// @formatter:on
	}

	@Test
	void listaAuthor_PaginacaoIgualDois_Sucesso() throws Exception {
		when(this.authorService.listaAuthor(any())).thenReturn(criarRespostaDTOAuthor(200, 2));

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
		Long id = 2L;
		String primeiron = "rod 1";
		String ultimon = "rosa 1";
		Date dataNasc = new Date();

		when(this.authorService.pesquisarPorId(id))
				.thenReturn(criarRespostaDTOAuthor(200, id, primeiron, ultimon, dataNasc));

		// @formatter:off
		mockMvc.perform(get(URL_PADRAO + "/{id}", id))
		       .andExpect(status().isOk())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			   .andExpect(jsonPath("$.codigo").value(200))
			   .andExpect(jsonPath("$.retorno.id").value(id))
			   .andExpect(jsonPath("$.retorno.firstName").value(primeiron))
			   .andExpect(jsonPath("$.retorno.lastName").value(ultimon))
			   .andExpect(jsonPath("$.retorno.birthday").value(
					   PaginUtil.formataDataPadraoDDMMYYYY(dataNasc)));
		// @formatter:off
	}

	@Test
	void pesquisarPorId_IdentificadorNaoExistente() throws Exception {
		when(this.authorService.pesquisarPorId(555L))
			.thenReturn(RespostaDTO.criarRespostaErroNotFound());
		
		// @formatter:off
		mockMvc.perform(get(URL_PADRAO + "/{id}", 555))
		       .andExpect(status().isNotFound())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			   .andExpect(jsonPath("$.codigo").value(404))
			   .andExpect(jsonPath("$.retorno").doesNotExist())
			   .andExpect(jsonPath("$.totalRegistros").value(0));
		// @formatter:off
	}
}
