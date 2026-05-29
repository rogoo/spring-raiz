## Spring @ImportHttpServices e @HttpExchange
O @HttpExchange permite criar chamadas RestClient a enpoints. O @ImportHttpServices permite configurar os beans em uma classe @Configuration.

```
@Configuration(proxyBeanMethods = false)
@ImportHttpServices(TodoExchangeService.class)
public class HttpClientConfig {
}
```

```
@HttpExchange(url = "https://jsonplaceholder.typicode.com/", accept = "application/json")
public interface TodoExchangeService {

    @GetExchange("/todos")
    List<Todo> getAllTodos();
	
	@PostExchange("/todos")
    Todo createTodo(@RequestBody Todo todo);

    @PutExchange("/todos/{id}")
    Todo updateTodo(@PathVariable Long id, @RequestBody Todo todo);

    @DeleteExchange("/todos/{id}")
    void deleteTodo(@PathVariable Long id);
}
```