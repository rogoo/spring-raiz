package br.rosa.pagin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.rosa.pagin.dto.AuthorDTO;
import br.rosa.pagin.dto.PostDTO;
import br.rosa.pagin.dto.response.RespostaDTO;
import br.rosa.pagin.mapper.AuthorMapper;
import br.rosa.pagin.model.Author;
import br.rosa.pagin.repository.AuthorRepository;
import br.rosa.pagin.repository.PostRepository;
import br.rosa.pagin.util.PaginMensagemUtil;
import br.rosa.pagin.util.PaginUtil;
import br.rosa.pagin.util.constantes.PaginMensagensConstantes;

@Service
public class AuthorService {

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	PostRepository postRepository;

	public RespostaDTO<List<AuthorDTO>> listaAuthor(Pageable pageable) {
		if (pageable.getPageSize() > 100) {
			return RespostaDTO.criarRespostaErro(PaginMensagensConstantes.MSG_LIMITE_RETORNO_REGISTROS);
		}

		return RespostaDTO.criarRespostaSucesso(
				AuthorMapper.converteModelToDTO(this.authorRepository.findAll(pageable).toList()),
				this.authorRepository.findAll().size());
	}

	public RespostaDTO<AuthorDTO> novoAuthor(AuthorDTO dto) {
		List<String> listaErros = validaInclusaoNovoAuthor(dto);

		if (!listaErros.isEmpty()) {
			return RespostaDTO.criarRespostaErro(listaErros);
		}

		this.authorRepository.save(new Author(dto.firstName(), dto.lastName(), dto.birthday()));

		return RespostaDTO.criarRespostaSucessoCreated();
	}

	private List<String> validaInclusaoNovoAuthor(AuthorDTO dto) {
		List<String> listaErro = new ArrayList<String>();

		if (!PaginUtil.validado(dto.firstName())) {
			listaErro.add(PaginMensagemUtil.criarMensagemCampoObrigatorio(AuthorDTO.campoFirstName()));
		}

		if (!PaginUtil.validado(dto.lastName())) {
			listaErro.add(PaginMensagemUtil.criarMensagemCampoObrigatorio(AuthorDTO.campoLastName()));
		}

		if (!PaginUtil.validado(dto.birthday())) {
			listaErro.add(PaginMensagemUtil.criarMensagemCampoObrigatorio(AuthorDTO.campoBirthday()));
		}

		return listaErro;
	}

	public RespostaDTO<AuthorDTO> pesquisarPorId(Long id) {
		Optional<Author> author = this.authorRepository.findById(id);

		if (author.isEmpty()) {
			return RespostaDTO.criarRespostaErroNotFound();
		}

		return RespostaDTO.criarRespostaSucesso(AuthorMapper.converteModelToDTO(author.get()));
	}

	public RespostaDTO<AuthorDTO> deletar(Long id) {
		if (this.authorRepository.findById(id).isEmpty()) {
			return RespostaDTO.criarRespostaErro(PaginMensagemUtil.criarMensagem(
					PaginMensagensConstantes.MSG_NAO_EXISTE_COM_IDENTIFICADOR, AuthorDTO.modelAuthor(), id));
		} else if (!this.postRepository.findByAuthorId(id).isEmpty()) {
			return RespostaDTO.criarRespostaErro(
					PaginMensagemUtil.criarMensagem(PaginMensagensConstantes.MSG_DELECAO_EXISTE_VINCULACAO,
							AuthorDTO.modelAuthor(), id, PostDTO.NOME_DTO));
		}

		this.authorRepository.deleteById(id);

		return RespostaDTO.criarRespostaSucesso();
	}

	public RespostaDTO<AuthorDTO> atualizarParcial(Long id, AuthorDTO authorFilter) {
		Optional<Author> authorBanco = this.authorRepository.findById(id);

		RespostaDTO<AuthorDTO> validacao = validaAtualizacaoParcial(id, authorFilter, authorBanco);

		if (validacao.getListaErro() != null) {
			return validacao;
		}

		Author aut = authorBanco.get();

		alterarEntidade(aut, authorFilter);

		Author authorAtualizado = this.authorRepository.save(aut);

		return RespostaDTO.criarRespostaSucesso(AuthorMapper.converteModelToDTO(authorAtualizado));
	}

	private RespostaDTO<AuthorDTO> validaAtualizacaoParcial(Long id, AuthorDTO authorFilter,
			Optional<Author> authorBanco) {
		RespostaDTO<AuthorDTO> retorno = new RespostaDTO<AuthorDTO>();

		if (authorBanco.isEmpty()) {
			return RespostaDTO.criarRespostaErro(PaginMensagemUtil.criarMensagem(
					PaginMensagensConstantes.MSG_NAO_EXISTE_COM_IDENTIFICADOR, AuthorDTO.modelAuthor(), id));
		} else {
			if (todosCamposVazios(authorFilter)) {
				return RespostaDTO.criarRespostaErro(PaginMensagensConstantes.MSG_SEM_ALTERACAO);
			}
		}

		return retorno;
	}

	private boolean todosCamposVazios(AuthorDTO authorFilter) {
		return authorFilter.firstName() == null && authorFilter.lastName() == null && authorFilter.birthday() == null;
	}

	public RespostaDTO<AuthorDTO> atualizarTotal(Long id, AuthorDTO authorFilter) {
		Optional<Author> authorBanco = this.authorRepository.findById(id);

		RespostaDTO<AuthorDTO> validacao = validaAtualizacaoTotal(id, authorFilter, authorBanco);

		if (validacao.getListaErro() != null) {
			return validacao;
		}

		Author aut = authorBanco.get();

		alterarEntidade(aut, authorFilter);

		Author authorSalvo = this.authorRepository.save(aut);

		return RespostaDTO.criarRespostaSucesso(AuthorMapper.converteModelToDTO(authorSalvo));
	}

	private RespostaDTO<AuthorDTO> validaAtualizacaoTotal(Long id, AuthorDTO authorFilter,
			Optional<Author> authorBanco) {
		RespostaDTO<AuthorDTO> retorno = new RespostaDTO<AuthorDTO>();

		if (authorBanco.isEmpty()) {
			return RespostaDTO.criarRespostaErro(PaginMensagemUtil.criarMensagem(
					PaginMensagensConstantes.MSG_NAO_EXISTE_COM_IDENTIFICADOR, AuthorDTO.modelAuthor(), id));
		} else if (algumCampoVazio(authorFilter)) {
			return RespostaDTO.criarRespostaErro(PaginMensagensConstantes.MSG_PREENCHA_TODOS_CAMPOS);
		}

		return retorno;
	}

	private boolean algumCampoVazio(AuthorDTO authorFilter) {
		return authorFilter.firstName() == null || authorFilter.lastName() == null || authorFilter.birthday() == null;
	}

	private void alterarEntidade(Author aut, AuthorDTO authorFilter) {
		if (authorFilter.firstName() != null) {
			aut.setFirstName(authorFilter.firstName());
		}

		if (authorFilter.lastName() != null) {
			aut.setLastName(authorFilter.lastName());
		}

		if (authorFilter.birthday() != null) {
			aut.setBirthday(authorFilter.birthday());
		}
	}
}
