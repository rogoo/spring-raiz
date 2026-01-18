package br.rosa.sdr.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.rosa.sdr.entity.Person;

@RepositoryRestResource(path = "person")
public interface PerRepository extends CrudRepository<Person, Integer>, PagingAndSortingRepository<Person, Integer> {

}