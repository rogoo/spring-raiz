package br.rosa.simple.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.rosa.simple.domain.Author;
import br.rosa.simple.domain.enumerator.SexoEnum;
import br.rosa.simple.exception.BadRequestException;
import br.rosa.simple.exception.EntityNotFound;
import br.rosa.simple.repository.AuthorRepository;
import br.rosa.simple.repository.PostRepository;
import br.rosa.simple.vo.AuthorVO;

@Service
public class AuthorService {

	private static final String AUTHOR_NAO_ENCONTRADO = "Author não encontrado";
	public final AuthorRepository authorRepository;
	public final PostRepository postRepository;

	public AuthorService(AuthorRepository authorRepository,
			PostRepository postRepository) {
		this.authorRepository = authorRepository;
		this.postRepository = postRepository;
	}

	public Author save(AuthorVO authorVO) {
		Author author = converteAuthor(authorVO);
		return this.authorRepository.save(author);
	}

	private Author converteAuthor(AuthorVO authorVO) {
		if (authorVO == null)
			return null;

		Author au = new Author();
		au.setActive(authorVO.getActive());
		au.setBirthday(LocalDate.parse(authorVO.getBirthday()));
		au.setFirstName(authorVO.getFirstName());
		au.setLastName(authorVO.getLastName());
		au.setSexo(SexoEnum.recuperarPeloCodigo(authorVO.getSexo()));
		return au;
	}

	public Author update(Long id, AuthorVO authorEnviado) {
		Author authorBanco = validaAtualizacaoAuthor(id, authorEnviado);

		return this.authorRepository
				.save(atualizaAuthor(authorBanco, authorEnviado));
	}

	private Author validaAtualizacaoAuthor(Long id, AuthorVO authorEnviado) {
		Author authorBanco = this.authorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFound(AUTHOR_NAO_ENCONTRADO));

		if (authorEnviado.getFirstName() != null && jaExistePrimeiroNomeCadastrado(
				authorEnviado.getFirstName(), id)) {
			throw new BadRequestException(
					"Jà existe um Author com mesmo nome. Escolha outro favorzin!");
		}

		return authorBanco;
	}

	private Author atualizaAuthor(Author authorBanco, AuthorVO authorEnviado) {
		if (Objects.nonNull(authorEnviado.getActive())) {
			authorBanco.setActive(authorEnviado.getActive());
		}

		if (Objects.nonNull(authorEnviado.getFirstName())) {
			authorBanco.setFirstName(authorEnviado.getFirstName());
		}

		if (Objects.nonNull(authorEnviado.getLastName())) {
			authorBanco.setLastName(authorEnviado.getLastName());
		}

		if (Objects.nonNull(authorEnviado.getBirthday())) {
			authorBanco.setBirthday(LocalDate.parse(authorEnviado.getBirthday()));
		}

		if (Objects.nonNull(authorEnviado.getSexo())) {
			authorBanco
					.setSexo(SexoEnum.recuperarPeloCodigo(authorEnviado.getSexo()));
		}

		return authorBanco;
	}

	private boolean jaExistePrimeiroNomeCadastrado(String pnome, Long id) {
		return this.authorRepository.verificaJaExistePrimeiroNome(pnome, id)
				.size() != 0;
	}

	public void delete(long id) {
		Author author = this.authorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFound(AUTHOR_NAO_ENCONTRADO));

		if (!Optional.ofNullable(author).map(Author::getListaPost).map(List::isEmpty)
				.orElse(true)) {
			throw new BadRequestException(
					"Não é possível excluir pois já existe Posts vinculados ao Author");
		}

		this.authorRepository.deleteById(id);
	}

	public Author findById(Long id) {
		return this.authorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFound(AUTHOR_NAO_ENCONTRADO));
	}

	public List<Author> listaTodos(String firstName, String lastName) {
		List<Author> lista = this.authorRepository.listaTodos(firstName, lastName);
		if (Objects.nonNull(firstName) && firstName.equals("ro")) {
            throw new BadRequestException(
                    "Não é possível excluir pois já existe Posts vinculados ao Author");
        }
		lista.stream().forEach(au -> au.setNaoPossuiPosts(Optional
				.ofNullable(au.getListaPost()).map(List::isEmpty).orElse(true)));

		return lista;
	}
}
