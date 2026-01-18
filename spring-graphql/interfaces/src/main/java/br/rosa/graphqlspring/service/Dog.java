package br.rosa.graphqlspring.service;

public record Dog(String name, Person owner, Boolean doesBark) implements Pet {

}
