package br.rosa.pagin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.rosa.pagin.controller.base.BaseController;
import br.rosa.pagin.dto.AuthorDTO;
import br.rosa.pagin.dto.PostDTO;
import br.rosa.pagin.dto.response.RespostaDTO;
import br.rosa.pagin.service.AuthorService;
import br.rosa.pagin.service.PostService;

@RestController
@RequestMapping(path = "/v1/authors", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController extends BaseController {

	@Autowired
	private AuthorService authorService;
	@Autowired
	private PostService postService;

	@GetMapping
	public ResponseEntity<RespostaDTO<List<AuthorDTO>>> listaAuthor(Pageable pageable) {
		return validaRetorno(this.authorService.listaAuthor(pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespostaDTO<AuthorDTO>> pesquisarPorId(@PathVariable Long id) {
		return validaRetorno(this.authorService.pesquisarPorId(id));
	}

	@GetMapping("/{id}/posts")
	public ResponseEntity<RespostaDTO<List<PostDTO>>> pesquisarPostsDoAuthor(@PathVariable Long id) {
		return validaRetorno(this.postService.pesquisarPorAuthor(id));
	}

	@PostMapping
	public ResponseEntity<RespostaDTO<AuthorDTO>> novoAuthor(@RequestBody AuthorDTO dto) {
		return validaRetorno(this.authorService.novoAuthor(dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RespostaDTO<AuthorDTO>> deletar(@PathVariable Long id) {
		return validaRetorno(this.authorService.deletar(id));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<RespostaDTO<AuthorDTO>> atualizarParcial(@PathVariable Long id,
			@RequestBody AuthorDTO authorFilter) {
		return validaRetorno(this.authorService.atualizarParcial(id, authorFilter));
	}

	@PutMapping("/{id}")
	public ResponseEntity<RespostaDTO<AuthorDTO>> atualizarTudo(@PathVariable Long id,
			@RequestBody AuthorDTO authorFilter) {
		return validaRetorno(this.authorService.atualizarTotal(id, authorFilter));
	}
}
