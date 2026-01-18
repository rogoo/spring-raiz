package br.rosa.graphqlspring.service.inter;

import br.rosa.graphqlspring.service.Person;

public interface Pet {

    String name();

    Person owner();
}
