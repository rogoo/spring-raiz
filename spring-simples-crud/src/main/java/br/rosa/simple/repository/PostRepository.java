package br.rosa.simple.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.rosa.simple.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
