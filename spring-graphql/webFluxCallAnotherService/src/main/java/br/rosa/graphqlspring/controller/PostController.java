package br.rosa.graphqlspring.controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;

import br.rosa.graphqlspring.service.Post;
import br.rosa.graphqlspring.service.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class PostController {

    WebClient webClient;

    public PostController(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://jsonplaceholder.typicode.com/").build();
    }

    @QueryMapping
    Flux<Post> posts() {
        return webClient.get().uri("/posts").retrieve().bodyToFlux(Post.class);
    }

    @SchemaMapping
    Mono<User> user(Post post) {
        return webClient.get().uri("/users/{id}", post.userId()).retrieve().bodyToMono(User.class);
    }
}
