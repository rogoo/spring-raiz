package br.rosa.graphqlspring.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import br.rosa.graphqlspring.service.Person;
import br.rosa.graphqlspring.service.Pet;

@Controller
public class PetController {

    private static final List<Pet> listPet = List.of(new Pet("Luna", new Person("1", "Rod", "Ros")),
            new Pet("Skippera", new Person("2", "Iza", "Amol")), new Pet("Patroa", null));

    @QueryMapping
    public List<Pet> favoritePet() {
        return listPet;
    }

    @SchemaMapping(typeName = "Pet", field = "owner")
    public Person owner(Pet pet) {
        return listPet.stream().filter(e -> e.owner() != null && e.owner().id().equals(pet.owner().id()))
                .map(Pet::owner).findFirst().orElse(null);
    }
}
