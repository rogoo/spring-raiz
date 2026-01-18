# About
Creating custom **scalar**.

In this project we defined a Date custom scalar. To do it we had to add a maven dependendy.
```
<dependency>
    <groupId>com.graphql-java</groupId>
    <artifactId>graphql-java-extended-scalars</artifactId>
    <version>22.0</version>
</dependency>
```

### Spring Scalar Configuration
```
@Configuration
public class GraphQLConfig {

    @Bean
    RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.Date);
    }
}
```

### Schema and Query
To add a custom scalar to the schema.
```
scalar Date
```

You can also specify documentation.
```
scalar Date @specifiedBy(url:"https://scalars.graphql.org/andimarek/local-date")
```

And the query
```
 query die {
  favoritePet {
    name
    dateOfBirth
  }
}
```
