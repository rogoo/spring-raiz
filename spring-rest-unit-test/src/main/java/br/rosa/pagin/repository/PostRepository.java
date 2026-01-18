package br.rosa.pagin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.rosa.pagin.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByAuthorId(Long id);
}
