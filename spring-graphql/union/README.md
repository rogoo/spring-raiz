# About
Example of a union.

### Schema
```
type Query {
  creatures: [Creature]
}

union Creature = Dog | Cat | Human
```

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
