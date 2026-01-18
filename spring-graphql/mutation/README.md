# About
A mutation example.

The schema.
```
type Mutation {
  changePostTitle(id: ID!, newTitle: String!): Post
}
```

The java code.
```
@MutationMapping
Mono<Post> changePostTitle(@Argument Integer id, @Argument String newTitle) {
  Map<String, String> changeTitleBody = Map.of("title", newTitle);

  return webClient.put()
	  .uri("/posts/{id}", id)
	  .contentType(MediaType.APPLICATION_JSON)
	  .body(BodyInserters.fromValue(changeTitleBody))
	  .retrieve()
	  .bodyToMono(Post.class);
    }
```


#### Query
```
mutation changeName {
  changePostTitle(id: 1, newTitle: "dieeeee") {
    id
    title
    body
  }
}
```