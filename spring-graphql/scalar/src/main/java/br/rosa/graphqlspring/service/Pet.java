package br.rosa.graphqlspring.service;

import java.time.temporal.TemporalAccessor;

public record Pet(String name, Person owner, TemporalAccessor dateOfBirth) {

}
