package br.rosa.pagin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.rosa.pagin.controller.base.BaseController;
import br.rosa.pagin.dto.PostDTO;
import br.rosa.pagin.exception.NotFoundException;
import br.rosa.pagin.model.Author;
import br.rosa.pagin.model.Post;
import br.rosa.pagin.repository.AuthorRepository;
import br.rosa.pagin.repository.PostRepository;
import br.rosa.pagin.service.AuthorService;
import br.rosa.pagin.service.PostService;
import br.rosa.pagin.util.PaginMensagemUtil;
import br.rosa.pagin.util.PaginUtil;
import br.rosa.pagin.util.constantes.PaginConstantes;
import br.rosa.pagin.util.constantes.PaginMensagensConstantes;

@RestController
@RequestMapping(path = "/v1/posts")
public class PostController extends BaseController {

	private final PostRepository postRepository;
	private final AuthorRepository authorRepository;
	private final PostService postService;
	private final AuthorService authorService;

	public PostController(PostRepository postRepository, AuthorRepository authorRepository, PostService postService,
			AuthorService authorService) {
		this.postRepository = postRepository;
		this.authorRepository = authorRepository;
		this.postService = postService;
		this.authorService = authorService;
	}

	@GetMapping
	public @ResponseBody List<Post> lista() {
		List<Post> listaRetorno = this.postRepository.findAll();

		return listaRetorno;
	}

	@GetMapping("/{id}/authors")
	public @ResponseBody Author pesquisaAuthor(@PathVariable Long id) {
		if (this.authorRepository.findById(id).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return this.postRepository.findById(id).map(post -> {
			return post.getAuthor();
		}).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					PaginMensagemUtil.criarMensagem(PaginConstantes.CARAC_VAZIO,
							PaginMensagensConstantes.MSG_NAO_EXISTE_COM_IDENTIFICADOR, "Post", id));
		});
	}

	@PostMapping
	public @ResponseBody Post novo(@RequestBody Post post) {
		// validação bem rapida... rs
		realizaValidacao(
				!PaginUtil.validado(post.getIdAuthor()) || !PaginUtil.validado(post.getIdAuthor()) ? "Informe o Author"
						: PaginConstantes.CARAC_VAZIO);

		Optional<Author> authorOp = this.authorRepository.findById(post.getIdAuthor());

		if (authorOp.isEmpty()) {
			realizaValidacao(PaginMensagemUtil.criarMensagem(PaginConstantes.CARAC_VAZIO,
					PaginMensagensConstantes.MSG_NAO_EXISTE_COM_IDENTIFICADOR, "Author", post.getAuthor().getId()));
		}

		post.setAuthor(authorOp.get());

		return this.postRepository.save(post);
	}

	@GetMapping("/{id}")
	public @ResponseBody Post pesquisarPorId(@PathVariable Long id) {
		return this.postRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		if (this.postRepository.findById(id).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		this.postRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	public Post atualizar(@PathVariable Long id, @RequestBody PostDTO postFilter) {
		return this.postRepository.findById(id).map(post -> {
			if (postFilter.title() != null) {
				post.setTitle(postFilter.title());
			}
			if (postFilter.description() != null) {
				post.setDescription(postFilter.description());
			}
			if (postFilter.idAuthor() != null) {
				post.setAuthor(this.authorRepository.findById(postFilter.idAuthor()).get());
			}

			return this.postRepository.save(post);
		}).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, PaginMensagensConstantes.MSG_OCORREU_ERRO));
	}
}
