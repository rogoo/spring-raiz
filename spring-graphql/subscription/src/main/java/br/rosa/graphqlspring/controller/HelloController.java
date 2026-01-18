package br.rosa.graphqlspring.controller;

import java.time.Duration;
import java.util.List;

import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;

@Controller
public class HelloController {

    @SubscriptionMapping
    Flux<String> hello() {
        Flux<Integer> interval = Flux.fromIterable(List.of(1, 2, 3)).delayElements(Duration.ofSeconds(1));

        return interval.map(e -> "Hello " + e);
    }
}
