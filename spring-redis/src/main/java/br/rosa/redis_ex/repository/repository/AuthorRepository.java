package br.rosa.redis_ex.repository.repository;

import br.rosa.redis_ex.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
