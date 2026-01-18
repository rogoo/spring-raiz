package br.rosa.pagin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rosa.pagin.dto.PostDTO;
import br.rosa.pagin.dto.response.RespostaDTO;
import br.rosa.pagin.mapper.PostMapper;
import br.rosa.pagin.model.Post;
import br.rosa.pagin.repository.AuthorRepository;
import br.rosa.pagin.repository.PostRepository;
import br.rosa.pagin.util.PaginMensagemUtil;
import br.rosa.pagin.util.PaginUtil;
import br.rosa.pagin.util.constantes.PaginMensagensConstantes;

@Service
public class PostService {

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	PostRepository postRepository;

	@Autowired
	PostMapper postMapper;

	public RespostaDTO<List<PostDTO>> pesquisarPorAuthor(Long idAuthor) {
		if (this.authorRepository.findById(idAuthor).isEmpty()) {
			return RespostaDTO.criarRespostaErro(PaginMensagemUtil
					.criarMensagem(PaginMensagensConstantes.MSG_NAO_EXISTE_COM_IDENTIFICADOR, "Author", idAuthor));
		}

		List<Post> listaPostBanco = this.postRepository.findByAuthorId(idAuthor);

		return RespostaDTO.criarRespostaSucesso(postMapper.postToPostDTO(listaPostBanco), listaPostBanco.size());
	}

	/**
	 * Valida a inclusao de um post.
	 * 
	 * @param dto
	 * @return
	 */
	public List<String> validaInclusao(PostDTO dto) {
		List<String> listaErros = new ArrayList<String>();

		if (!PaginUtil.validado(dto.title())) {
			listaErros.add(PaginMensagemUtil.criarMensagemCampoObrigatorio(PostDTO.CAMPO_TITLE));
		}

		if (!PaginUtil.validado(dto.description())) {
			listaErros.add(PaginMensagemUtil.criarMensagemCampoObrigatorio(PostDTO.CAMPO_DESCRIPTION));
		}

		return listaErros;
	}

	/**
	 * Valida a alteração.
	 * 
	 * @param idPost
	 * @param dto
	 * @return
	 */
	public String validaAlteracao(Long idPost, PostDTO dto) {
		StringBuilder msg = new StringBuilder(1000);

		if (!this.postRepository.findById(idPost).isPresent()) {
			msg.append(
					PaginMensagemUtil.criarMensagem(PaginMensagensConstantes.MSG_NAO_EXISTE_COM_IDENTIFICADOR, "Post", idPost));
		}

		return msg.toString();
	}
}
