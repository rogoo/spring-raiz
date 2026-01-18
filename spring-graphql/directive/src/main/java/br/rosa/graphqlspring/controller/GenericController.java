package br.rosa.graphqlspring.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import br.rosa.graphqlspring.service.Pet;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLAppliedDirective;
import graphql.schema.GraphQLFieldDefinition;

@Controller
public class GenericController {

    @QueryMapping
    public List<Pet> pets(DataFetchingEnvironment env) {
        GraphQLFieldDefinition fieldDef = env.getFieldDefinition();
        GraphQLAppliedDirective important = fieldDef.getAppliedDirective("important");

        if (important != null) {
//            System.out
//                    .println(((graphql.language.StringValue) important.getDefinition().getArguments().get(0).getValue())
//                            .getValue());
            // or do something important
        }

        return List.of(new Pet(1, "Rod", "yellow"), new Pet(44, "Izz", "jos"), new Pet(661, "Ige", null));
    }
}
