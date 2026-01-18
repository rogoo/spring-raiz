# About
Example of a custom directive and printing its value.


### Java
```
@Controller
public class GenericController {

    @QueryMapping
    public List<Pet> pets(DataFetchingEnvironment env) {
        GraphQLFieldDefinition fieldDef = env.getFieldDefinition();
        GraphQLAppliedDirective important = fieldDef.getAppliedDirective("important");

        if (important != null) {
            System.out
                    .println(((graphql.language.StringValue) important.getDefinition().getArguments().get(0).getValue())
                            .getValue());
            // or do something important
        }

        return List.of(new Pet(1, "Rod", "yellow"), new Pet(44, "Izz", "jos"), new Pet(661, "Ige", null));
    }
}
```

### Schema
```
directive @important(desc: String!) on FIELD_DEFINITION

type Query {
  pets: [Pet] @important(desc: "die and die")
}

type Pet {
  id: ID!
  name: String
  color: String
}
```

#### Query
```
query die {
  pets {
    id
    name
  }
}
```
