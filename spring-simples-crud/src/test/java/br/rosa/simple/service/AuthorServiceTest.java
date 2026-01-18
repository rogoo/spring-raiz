package br.rosa.simple.service;

import br.rosa.simple.domain.Author;
import br.rosa.simple.domain.Post;
import br.rosa.simple.exception.BadRequestException;
import br.rosa.simple.exception.EntityNotFound;
import br.rosa.simple.repository.AuthorRepository;
import br.rosa.simple.repository.PostRepository;
import br.rosa.simple.vo.AuthorVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private PostRepository postRepository;

    private AuthorService authorService;

    @BeforeEach
    void setup() {
        authorService = new AuthorService(authorRepository, postRepository);
    }

    @Test
    void save_Sucesso() {
        AuthorVO vo = new AuthorVO("John", "Doe");
        // ensure birthday and sexo not null to avoid parsing issues
        vo.setBirthday("1990-01-01");
        vo.setActive(true);
        vo.setSexo("M");

        when(authorRepository.save(ArgumentMatchers.any(Author.class))).thenAnswer(invocation -> {
            Author arg = invocation.getArgument(0);
            arg.setId(1L);
            return arg;
        });

        Author saved = authorService.save(vo);

        assertThat(saved, notNullValue());
        assertThat(saved.getId(), is(1L));
        assertThat(saved.getFirstName(), is("John"));
        assertThat(saved.getBirthday(), is(LocalDate.parse("1990-01-01")));
        verify(authorRepository).save(ArgumentMatchers.any(Author.class));
    }

    @Test
    void update_Sucesso() {
        Author existing = new Author(1L, "John", "Doe");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(authorRepository.verificaJaExistePrimeiroNome("Johnny", 1L)).thenReturn(new ArrayList<>());
        when(authorRepository.save(ArgumentMatchers.any(Author.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AuthorVO vo = new AuthorVO("Johnny", null);
        vo.setLastName("Updated");

        Author updated = authorService.update(1L, vo);

        assertThat(updated.getFirstName(), is("Johnny"));
        assertThat(updated.getLastName(), is("Updated"));
        verify(authorRepository).findById(1L);
        verify(authorRepository).save(ArgumentMatchers.any(Author.class));
    }

    @Test
    void update_LancaBadRequest_PrimeiroNomeJaExistir() {
        Author existing = new Author(1L, "John", "Doe");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(existing));
        List<Author> conflict = List.of(new Author(2L, "Johnny", "Other"));
        when(authorRepository.verificaJaExistePrimeiroNome("Johnny", 1L)).thenReturn(conflict);

        AuthorVO vo = new AuthorVO("Johnny", null);

        assertThrows(BadRequestException.class, () -> authorService.update(1L, vo));

        verify(authorRepository).findById(1L);
        verify(authorRepository).verificaJaExistePrimeiroNome("Johnny", 1L);
        verify(authorRepository, never()).save(any());
    }

    @Test
    void update_LancaEntityNotFound_NaoExiste() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        AuthorVO vo = new AuthorVO("Any", "Name");

        assertThrows(EntityNotFound.class, () -> authorService.update(1L, vo));

        verify(authorRepository).findById(1L);
        verify(authorRepository, never()).save(any());
    }

    @Test
    void delete_Sucesso_SemPosts() {
        Author author = mock(Author.class);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(author.getListaPost()).thenReturn(null);

        authorService.delete(1L);

        verify(authorRepository).findById(1L);
        verify(authorRepository).deleteById(1L);
    }

    @Test
    void delete_LancaBadRequest_NaoPossuiPosts() {
        Author author = mock(Author.class);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(author.getListaPost()).thenReturn(List.of(new Post()));

        assertThrows(BadRequestException.class, () -> authorService.delete(1L));

        verify(authorRepository).findById(1L);
        verify(authorRepository, never()).deleteById(anyLong());
    }

    @Test
    void findById_RetornaAuthorSeAchou_LancaExcecaoSeNaoAchou() {
        Author author = new Author(1L, "John", "Doe");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Author got = authorService.findById(1L);
        assertThat(got.getId(), is(1L));

        when(authorRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFound.class, () -> authorService.findById(2L));
    }

    @Test
    void listaTodos_NaoPossuiPosts_BadRequest() {
        Author a1 = new Author(1L, "Alice", "A");
        a1.setListaPost(null);
        Author a2 = new Author(2L, "Bob", "B");
        a2.setListaPost(List.of(new Post()));

        when(authorRepository.listaTodos(null, null)).thenReturn(List.of(a1, a2));
        List<Author> result = authorService.listaTodos(null, null);

        assertThat(result, hasSize(2));
        assertThat(result.get(0).isNaoPossuiPosts(), is(true));
        assertThat(result.get(1).isNaoPossuiPosts(), is(false));

        // when firstName equals "ro" service should throw BadRequestException
        // after calling repository
        when(authorRepository.listaTodos("ro", null)).thenReturn(List.of(a1));
        assertThrows(BadRequestException.class, () -> authorService.listaTodos("ro"
                , null));
        verify(authorRepository).listaTodos("ro", null);
    }
}
