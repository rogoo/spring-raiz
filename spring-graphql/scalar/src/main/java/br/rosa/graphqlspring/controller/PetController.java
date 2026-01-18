package br.rosa.graphqlspring.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import br.rosa.graphqlspring.service.Person;
import br.rosa.graphqlspring.service.Pet;

@Controller
public class PetController {

    private static final List<Pet> listPet = List.of(
            new Pet("Luna", new Person("1", "Rod", "Ros"), DateTimeFormatter.ISO_LOCAL_DATE.parse("2022-03-22")),
            new Pet("Skippera", new Person("2", "Iza", "Amol"), DateTimeFormatter.ISO_LOCAL_DATE.parse("2024-06-12")),
            new Pet("Patra", null, null));

    @QueryMapping
    public List<Pet> favoritePet() {
        return listPet;
    }

    @QueryMapping
    public List<Pet> favoritePetByName(@Argument("name") String anotherName) {
        return listPet.stream().filter(e -> e.name().contains(anotherName)).collect(Collectors.toList());
    }

    @SchemaMapping
    public Person owner(Pet pet) {
        return listPet.stream()
                .filter(e -> e.owner() != null && pet.owner() != null && e.owner().id().equals(pet.owner().id()))
                .map(Pet::owner).findFirst().orElse(null);
    }
}
