package br.rosa.pagin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.rosa.pagin.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
