package br.rosa.graphqlspring.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import br.rosa.graphqlspring.service.Pet;
import graphql.execution.directives.QueryAppliedDirective;
import graphql.execution.directives.QueryAppliedDirectiveArgument;
import graphql.execution.directives.QueryDirectives;
import graphql.schema.DataFetchingEnvironment;

@Controller
public class GenericController {

    @QueryMapping
    public List<Pet> pets(DataFetchingEnvironment env) {
        QueryDirectives queryDirectives = env.getQueryDirectives();
        List<QueryAppliedDirective> cacheDirectives = queryDirectives.getImmediateAppliedDirective("cache");

        if (cacheDirectives.size() > 0) {
            QueryAppliedDirective cache = cacheDirectives.get(0);
            QueryAppliedDirectiveArgument maxAgeArgument = cache.getArgument("maxAge");
            int maxAge = maxAgeArgument.getValue();
            System.out.println(maxAge);
        }

        return List.of(new Pet(1, "Rod", "yellow"), new Pet(44, "Izz", "jos"), new Pet(661, "Ige", null));
    }
}
