package br.rosa.simple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.rosa.simple.domain.Author;
import jakarta.annotation.Nullable;

// Testando merge
public interface AuthorRepository extends JpaRepository<Author, Long> {

	@Query(value = "select a from Author a where (:firstName is null or a.firstName like %:firstName%) and (:lastName is null or a.lastName like %:lastName%)")
	List<Author> listaTodos(@Param("firstName") String firstName, String lastName);

	List<Author> findByFirstNameContaining(@Nullable String firstName);

	@Query(value = "from Author au where upper(au.firstName) = upper(:firstName) and au.id != :id")
	List<Author> verificaJaExistePrimeiroNome(String firstName, Long id);
}
