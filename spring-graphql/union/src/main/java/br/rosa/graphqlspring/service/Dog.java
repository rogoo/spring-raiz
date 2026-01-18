package br.rosa.graphqlspring.service;

import br.rosa.graphqlspring.service.inter.Pet;

public record Dog(Integer id, String name, String color, boolean barks) implements Pet {

}
