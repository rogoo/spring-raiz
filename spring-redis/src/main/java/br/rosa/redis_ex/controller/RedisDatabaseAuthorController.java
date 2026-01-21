package br.rosa.redis_ex.controller;

import br.rosa.redis_ex.domain.Author;
import br.rosa.redis_ex.service.AuthorService;
import br.rosa.redis_ex.vo.AuthorVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis-database/author")
public class RedisDatabaseAuthorController {

    private final AuthorService authorService;

    public RedisDatabaseAuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/{id}")
    public Author findAuthorById(@PathVariable Long id) {
        return this.authorService.findAuthorById(id);
    }

    @PutMapping
    public Long saveAuthor(@RequestBody AuthorVO authorVO) {
        return this.authorService.saveAuthor(authorVO).getId();
    }

    @PatchMapping("/{id}")
    public Author patchAuthor(@PathVariable Long id, @RequestBody Author authorChanged) {
        return this.authorService.patchAuthor(id, authorChanged);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        this.authorService.deleteAuthor(id);
    }
}
