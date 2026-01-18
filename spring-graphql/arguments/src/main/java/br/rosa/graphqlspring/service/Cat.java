package br.rosa.graphqlspring.service;

public record Cat(String name, Person owner, Boolean doesMeow) implements Pet {

}
