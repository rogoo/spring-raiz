package br.rosa.graphqlspring.controller;

import java.util.Map;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import br.rosa.graphqlspring.service.Post;
import br.rosa.graphqlspring.service.PostSearchInput;
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

    @QueryMapping
    Mono<Post> post(@Argument Integer id) {
        return webClient.get().uri("/posts/{id}", id).retrieve().bodyToMono(Post.class);
    }

    @QueryMapping
    Flux<Post> postSearch(@Argument PostSearchInput input) {
        System.out.println(input.title() + " - " + input.body());
        return webClient.get().uri("/posts").retrieve().bodyToFlux(Post.class)
                .filter(e -> e.title().contains(input.title()));

    }

    @SchemaMapping
    Mono<User> user(Post post) {
        return webClient.get().uri("/users/{id}", post.userId()).retrieve().bodyToMono(User.class);
    }

    @MutationMapping
    Mono<Post> changePostTitle(@Argument Integer id, @Argument String newTitle) {
        Map<String, String> changeTitleBody = Map.of("title", newTitle);

        return webClient.put().uri("/posts/{id}", id).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(changeTitleBody)).retrieve().bodyToMono(Post.class);
    }
}
