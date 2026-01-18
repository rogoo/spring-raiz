package br.rosa.simple.controller;

import br.rosa.simple.domain.Author;
import br.rosa.simple.exception.EntityNotFound;
import br.rosa.simple.service.AuthorService;
import br.rosa.simple.vo.AuthorVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

    private AuthorService authorService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        authorService = mock(AuthorService.class);
        AuthorController controller = new AuthorController(authorService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listaTodos_SemFiltro() throws Exception {
        List<Author> authors = List.of(
                new Author(1L, "John", "Doe"),
                new Author(2L, "Jane", "Smith")
        );
        when(authorService.listaTodos(null, null)).thenReturn(authors);

        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[1].firstName", is("Jane")));
    }

    @Test
    void listaTodos_ComFiltroPrimeiroNome() throws Exception {
        List<Author> authors = List.of(new Author(1L, "John", "Doe"));
        when(authorService.listaTodos("John", null)).thenReturn(authors);

        mockMvc.perform(get("/authors").param("firstName", "John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Doe")));
    }

    @Test
    void recuperarAuthorPorId_Achou() throws Exception {
        Author author = new Author(1L, "John", "Doe");
        when(authorService.findById(1L)).thenReturn(author);

        mockMvc.perform(get("/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")));
    }

    @Test
    void recuperarAuthorPorId_NaoAchou_LancaExcecao() throws Exception {
        when(authorService.findById(1L)).thenThrow(EntityNotFound.class);

        mockMvc.perform(get("/authors/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void salvarAuthor_RetornaId() throws Exception {
        AuthorVO authorVO = new AuthorVO("John", "Doe");
        Author saved = new Author(1L, "John", "Doe");
        when(authorService.save(any(AuthorVO.class))).thenReturn(saved);

        mockMvc.perform(put("/authors")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(authorVO)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void atualizarAuthor_Sucesso() throws Exception {
        AuthorVO authorVO = new AuthorVO("John", "Smith");
        Author updated = new Author(1L, "John", "Smith");
        when(authorService.update(eq(1L), any(AuthorVO.class))).thenReturn(updated);

        mockMvc.perform(patch("/authors/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(authorVO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.lastName", is("Smith")));
    }

    @Test
    void deletarAuthor_Sucesso() throws Exception {
        doNothing().when(authorService).delete(1L);

        mockMvc.perform(delete("/authors/1"))
                .andExpect(status().isOk());

        verify(authorService).delete(1L);
    }
}
