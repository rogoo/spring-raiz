package br.rosa.graphqlspring.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import br.rosa.graphqlspring.service.Cat;
import br.rosa.graphqlspring.service.Dog;
import br.rosa.graphqlspring.service.Person;
import br.rosa.graphqlspring.service.Pet;

@Controller
public class PetController {

    private static final List<Pet> listPet = List.of(new Dog("Luna", new Person("1", "Rod", "Ros"), false),
            new Cat("Skippera", new Person("2", "Iza", "Amol"), true), new Cat("Patra", null, null));

    @QueryMapping
    public List<Pet> favoritePet() {
        return listPet;
    }

    @QueryMapping
    public List<Pet> favoritePetByName(@Argument String name) {
        return listPet.stream().filter(e -> e.name().contains(name)).collect(Collectors.toList());
    }
	
	//@QueryMapping
    //public List<Pet> favoritePetByName(@Argument("name") String anotherName) {
    //    return listPet.stream().filter(e -> e.name().contains(anotherName)).collect(Collectors.toList());
    //}

    @SchemaMapping
    public Person owner(Cat cat) {
        return listPet.stream()
                .filter(e -> e.owner() != null && cat.owner() != null && e.owner().id().equals(cat.owner().id()))
                .map(Pet::owner).findFirst().orElse(null);
    }

    @SchemaMapping
    public Person owner(Dog dog) {
        return listPet.stream()
                .filter(e -> e.owner() != null && dog.owner() != null && e.owner().id().equals(dog.owner().id()))
                .map(Pet::owner).findFirst().orElse(null);
    }
}
