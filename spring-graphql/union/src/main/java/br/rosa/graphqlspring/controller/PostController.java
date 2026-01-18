package br.rosa.graphqlspring.controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import br.rosa.graphqlspring.service.Cat;
import br.rosa.graphqlspring.service.Dog;
import br.rosa.graphqlspring.service.Human;
import reactor.core.publisher.Flux;

@Controller
public class PostController {

    @QueryMapping
    Flux<Object> creatures() {
        return Flux.just(new Dog(666, "Dog 1", "White", true), new Cat(4, "Cat 2", "Black", false), new Human("Rod"));
    }
}
