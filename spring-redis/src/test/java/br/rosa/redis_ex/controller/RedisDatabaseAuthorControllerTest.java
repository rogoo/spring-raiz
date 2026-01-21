package br.rosa.redis_ex.controller;

import br.rosa.redis_ex.domain.Author;
import br.rosa.redis_ex.service.AuthorService;
import br.rosa.redis_ex.vo.AuthorVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RedisDatabaseAuthorControllerTest {

    @Mock
    private AuthorService authorService;

    private RedisDatabaseAuthorController controller;

    @BeforeEach
    void setUp() {
        controller = new RedisDatabaseAuthorController(authorService);
    }

    @Test
    void findAuthorById_RetornaAuthor() {
        Author author = new Author(1L, "Rod", "Rocha");
        when(authorService.findAuthorById(1L)).thenReturn(author);

        Author resultado = controller.findAuthorById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Rod", resultado.getFirstName());
        assertEquals("Rocha", resultado.getLastName());
    }

    @Test
    void saveAuthor_retornaIdDoAuthor() {
        Author authorSalvo = new Author(2L, "Daniel", "Rocha");
        AuthorVO authorVO = new AuthorVO(null, "Daniel", "Rocha", null, null, null);
        when(authorService.saveAuthor(any(AuthorVO.class))).thenReturn(authorSalvo);

        Long id = controller.saveAuthor(authorVO);

        assertNotNull(id);
        assertEquals(2L, id);
    }

    @Test
    void patchAuthor_retornaAuthorAtualizado() {
        Author changed = new Author(3L, "Rodrigo", "Urtimo");
        when(authorService.patchAuthor(eq(3L), any(Author.class))).thenReturn(changed);

        Author patchPayload = new Author();
        patchPayload.setFirstName("Rodrigo");
        patchPayload.setLastName("Urtimo");

        Author result = controller.patchAuthor(3L, patchPayload);

        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("Rodrigo", result.getFirstName());
        assertEquals("Urtimo", result.getLastName());
    }

    @Test
    void deleteAuthor_VerificaServicoChamado() {
        when(authorService.deleteAuthor(4L)).thenReturn(true);

        controller.deleteAuthor(4L);

        verify(authorService).deleteAuthor(4L);
    }
}
