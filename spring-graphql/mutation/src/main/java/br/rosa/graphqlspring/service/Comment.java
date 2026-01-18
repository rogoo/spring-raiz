package br.rosa.graphqlspring.service;

public record Comment(Integer id, Integer postId, String name, String email) {

}
