package br.rosa.graphqlspring.service;

import br.rosa.graphqlspring.service.inter.Pet;

public record Cat(Integer id, String name, String color, boolean meows) implements Pet {

}
