# About
A GraphQL calling another service in a reactive way (Spring WebFlux).

We are using a fake JSON website.
> https://jsonplaceholder.typicode.com/

#### Query
```
query die {
  posts {
    id
    title
    user {
      username
      name
    }
  }
}
```
