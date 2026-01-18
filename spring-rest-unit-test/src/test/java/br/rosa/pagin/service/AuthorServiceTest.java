package br.rosa.pagin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.rosa.pagin.controller.base.BaseTest;
import br.rosa.pagin.dto.AuthorDTO;
import br.rosa.pagin.dto.PostDTO;
import br.rosa.pagin.dto.response.RespostaDTO;
import br.rosa.pagin.model.Author;
import br.rosa.pagin.model.Post;
import br.rosa.pagin.repository.AuthorRepository;
import br.rosa.pagin.repository.PostRepository;
import br.rosa.pagin.util.PaginMensagemUtil;
import br.rosa.pagin.util.constantes.PaginMensagensConstantes;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest extends BaseTest {

	@InjectMocks
	AuthorService authorService;

	@Mock
	AuthorRepository authorRepository;

	@Mock
	PostRepository postRepository;

	@Test
	void listaAuthor_Sucesso() {
		Pageable page = PageRequest.of(0, 10);
		when(this.authorRepository.findAll(page)).thenReturn(criarPageListAuthor(200, 10, page, 133));
		when(this.authorRepository.findAll()).thenReturn(criarListAuthor(133));

		RespostaDTO<List<AuthorDTO>> retorno = this.authorService.listaAuthor(page);

		assertThat(retorno.getCodigo()).isEqualTo(200);
		assertThat(retorno.getListaErro()).isNull();
		assertThat(retorno.getTotalRegistros()).isEqualTo(133);
		assertThat(retorno.getRetorno().size()).isEqualTo(10);
	}

	@Test
	void listaAuthor_PaginaSize100() {
		int totalPaginasGeral = 100;
		Pageable page = PageRequest.of(0, totalPaginasGeral);
		when(this.authorRepository.findAll(page)).thenReturn(criarPageListAuthor(200, totalPaginasGeral, page, 133));
		when(this.authorRepository.findAll()).thenReturn(criarListAuthor(133));

		RespostaDTO<List<AuthorDTO>> retorno = this.authorService.listaAuthor(page);

		assertThat(retorno.getCodigo()).isEqualTo(200);
		assertThat(retorno.getListaErro()).isNull();
		assertThat(retorno.getTotalRegistros()).isEqualTo(133);
		assertThat(retorno.getRetorno().size()).isEqualTo(totalPaginasGeral);
	}

	@Test
	void listaAuthor_PaginaSize101_Erro() {
		int totalPaginasGeral = 101;
		Pageable page = PageRequest.of(0, totalPaginasGeral);

		RespostaDTO<List<AuthorDTO>> retorno = this.authorService.listaAuthor(page);

		assertThat(retorno.getCodigo()).isEqualTo(400);
		assertThat(retorno.getListaErro()).isNotNull();
		assertThat(retorno.getListaErro().get(0).getMessage())
				.isEqualTo(PaginMensagensConstantes.MSG_LIMITE_RETORNO_REGISTROS);
	}

	@Test
	void novoAuthor_Sucesso() {
		String primeiro = "rod";
		String urtimo = "rosa";
		Date dataNasc = new Date();

		AuthorDTO authorDTO = new AuthorDTO(null, primeiro, urtimo, dataNasc);

		RespostaDTO<AuthorDTO> retorno = this.authorService.novoAuthor(authorDTO);

		assertThat(retorno.getCodigo()).isEqualTo(201);
	}

	@Test
	void novoAuthor_SemPrimeiroNome() {
		String primeiro = null;
		String urtimo = "rosa";
		Date dataNasc = new Date();

		AuthorDTO authorDTO = new AuthorDTO(null, primeiro, urtimo, dataNasc);

		RespostaDTO<AuthorDTO> retorno = this.authorService.novoAuthor(authorDTO);

		assertThat(retorno.getCodigo()).isEqualTo(400);
		assertThat(retorno.getListaErro().get(0).getMessage())
				.isEqualTo(PaginMensagemUtil.criarMensagemCampoObrigatorio(AuthorDTO.campoFirstName()));
	}

	@Test
	void novoAuthor_SemSegundoNome() {
		String primeiro = "rod";
		String urtimo = null;
		Date dataNasc = new Date();

		AuthorDTO authorDTO = new AuthorDTO(null, primeiro, urtimo, dataNasc);

		RespostaDTO<AuthorDTO> retorno = this.authorService.novoAuthor(authorDTO);

		assertThat(retorno.getCodigo()).isEqualTo(400);
		assertThat(retorno.getListaErro().get(0).getMessage())
				.isEqualTo(PaginMensagemUtil.criarMensagemCampoObrigatorio(AuthorDTO.campoLastName()));
	}

	@Test
	void novoAuthor_SemDataNascimenot() {
		String primeiro = "rod";
		String urtimo = "rosa";
		Date dataNasc = null;

		AuthorDTO authorDTO = new AuthorDTO(null, primeiro, urtimo, dataNasc);

		RespostaDTO<AuthorDTO> retorno = this.authorService.novoAuthor(authorDTO);

		assertThat(retorno.getCodigo()).isEqualTo(400);
		assertThat(retorno.getListaErro().get(0).getMessage())
				.isEqualTo(PaginMensagemUtil.criarMensagemCampoObrigatorio(AuthorDTO.campoBirthday()));
	}

	@Test
	void novoAuthor_SemNenhumDado() {
		String primeiro = null;
		String urtimo = null;
		Date dataNasc = null;

		AuthorDTO authorDTO = new AuthorDTO(null, primeiro, urtimo, dataNasc);

		RespostaDTO<AuthorDTO> retorno = this.authorService.novoAuthor(authorDTO);

		assertThat(retorno.getCodigo()).isEqualTo(400);
		assertThat(retorno.getListaErro().size()).isEqualTo(3);
	}

	@Test
	void pesquisarPorId_Sucesso() {
		Long id = 99L;
		String primeiro = "rod";
		String urtimo = "rosa";
		Date dataNasc = new Date();

		Optional<Author> authorOptional = Optional.of(new Author(99L, primeiro, urtimo, dataNasc));
		when(this.authorRepository.findById(id)).thenReturn(authorOptional);

		RespostaDTO<AuthorDTO> retorno = this.authorService.pesquisarPorId(id);

		assertThat(retorno.getCodigo()).isEqualTo(200);
		assertThat(retorno.getListaErro()).isNull();
		assertThat(retorno.getRetorno().id()).isEqualTo(id);
		assertThat(retorno.getRetorno().firstName()).isEqualTo(primeiro);
		assertThat(retorno.getRetorno().lastName()).isEqualTo(urtimo);
		assertThat(retorno.getRetorno().birthday()).isEqualTo(dataNasc);
	}

	@Test
	void pesquisarPorId_NaoEncontrouBanco() {
		when(this.authorRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

		RespostaDTO<AuthorDTO> retorno = this.authorService.pesquisarPorId(ArgumentMatchers.anyLong());

		assertThat(retorno.getCodigo()).isEqualTo(404);
	}

	@Test
	void deletar_Sucesso() {
		Long id = 45L;
		when(this.authorRepository.findById(id)).thenReturn(Optional.of(new Author()));
		when(this.postRepository.findByAuthorId(id)).thenReturn(new ArrayList<Post>());

		RespostaDTO<AuthorDTO> retorno = this.authorService.deletar(id);

		assertThat(retorno.getCodigo()).isEqualTo(200);
	}

	@Test
	void deletar_NaoExisteAuthor() {
		Long id = 45L;
		when(this.authorRepository.findById(id)).thenReturn(Optional.empty());

		RespostaDTO<AuthorDTO> retorno = this.authorService.deletar(id);

		assertThat(retorno.getCodigo()).isEqualTo(400);
		assertThat(retorno.getListaErro().size()).isEqualTo(1);
		assertThat(retorno.getListaErro().get(0).getMessage()).isEqualTo(PaginMensagemUtil
				.criarMensagem(PaginMensagensConstantes.MSG_NAO_EXISTE_COM_IDENTIFICADOR, AuthorDTO.modelAuthor(), id));
	}

	@Test
	void deletar_ExistePostVinculadoAoAuthor() {
		Long id = 45L;
		when(this.authorRepository.findById(id)).thenReturn(Optional.of(new Author()));
		when(this.postRepository.findByAuthorId(id)).thenReturn(Arrays.asList(new Post()));

		RespostaDTO<AuthorDTO> retorno = this.authorService.deletar(id);

		assertThat(retorno.getCodigo()).isEqualTo(400);
		assertThat(retorno.getListaErro().size()).isEqualTo(1);
		assertThat(retorno.getListaErro().get(0).getMessage()).isEqualTo(PaginMensagemUtil.criarMensagem(
				PaginMensagensConstantes.MSG_DELECAO_EXISTE_VINCULACAO, AuthorDTO.modelAuthor(), id, PostDTO.NOME_DTO));
	}
}
