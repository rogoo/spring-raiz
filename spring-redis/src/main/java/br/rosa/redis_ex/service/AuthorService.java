package br.rosa.redis_ex.service;

import br.rosa.redis_ex.domain.Author;
import br.rosa.redis_ex.repository.repository.AuthorRepository;
import br.rosa.redis_ex.vo.AuthorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Cacheable(value = "authorCache", key = "#id")
    public Author findAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    public Author saveAuthor(AuthorVO author) {
        return authorRepository.save(converteAuthorVOParaAuthorDB(author));
    }

    /**
     * Converte AuthorVO para Author para salvar no banco de dados.
     *
     * @param authorVO
     * @return
     */
    private Author converteAuthorVOParaAuthorDB(AuthorVO authorVO) {
        Author author = new Author();
        author.setId(authorVO.getId());
        author.setFirstName(authorVO.getFirstName());
        author.setLastName(authorVO.getLastName());
        author.setBirthday(authorVO.getBirthday());
        author.setActive(authorVO.getActive());
        author.setSexo(authorVO.getSexo());
        return author;
    }

    @CacheEvict(value = "authorCache", key = "#id")
    public Author patchAuthor(Long id, Author authorAlterado) {
        return authorRepository.findById(id).map(existing -> {
            if (authorAlterado.getFirstName() != null) {
                existing.setFirstName(authorAlterado.getFirstName());
            }
            if (authorAlterado.getLastName() != null) {
                existing.setLastName(authorAlterado.getLastName());
            }
            if (authorAlterado.getBirthday() != null) {
                existing.setBirthday(authorAlterado.getBirthday());
            }
            if (authorAlterado.getActive() != null) {
                existing.setActive(authorAlterado.getActive());
            }
            if (authorAlterado.getSexo() != null) {existing.setSexo(authorAlterado.getSexo());}
            // listaPost and transient fields are not patched here
            return authorRepository.save(existing);
        }).orElse(null);
    }

    @CacheEvict(value = "authorCache", key = "#id")
    public boolean deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            return false;
        }
        authorRepository.deleteById(id);
        return true;
    }
}
