package br.rosa.simple.controller;

import br.rosa.simple.domain.Author;
import br.rosa.simple.service.AuthorService;
import br.rosa.simple.vo.AuthorVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> listaTodos(@RequestParam(required = false) String firstName,
                                   @RequestParam(required = false) String lastName) {
        return this.authorService.listaTodos(firstName, lastName);
    }

    @GetMapping(value = "/{id}")
    public Author recuperarAuthorPorId(@PathVariable Long id) {
        return this.authorService.findById(id);
    }

    @PutMapping()
    public Long salvarAuthor(@RequestBody AuthorVO author) {
        return this.authorService.save(author).getId();
    }

    @PatchMapping(value = "/{id}")
    public Author atualizarAuthor(@PathVariable Long id,
                                  @RequestBody AuthorVO author) {
        return this.authorService.update(id, author);
    }

    @DeleteMapping("/{id}")
    public void deletarAuthor(@PathVariable Long id) {
        this.authorService.delete(id);
    }
}
