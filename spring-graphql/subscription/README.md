# About
Example of a subscription. Every one second it returns a message.


### Java
```
@Controller
public class HelloController {

    @SubscriptionMapping
    Flux<String> hello() {
        Flux<Integer> interval = Flux.fromIterable(List.of(1, 2, 3))
               .delayElements(Duration.ofSeconds(1));

        return interval.map(e -> "Hello " + e);
    }
}
```

### Schema
Unfortunately, you have to write a query, so we have a fake/not used. Go figure.
```
type Query {
  notUsed: String
}

type Subscription {
  hello: String
}
```

#### Query
```
subscription die {
  hello
}
```
