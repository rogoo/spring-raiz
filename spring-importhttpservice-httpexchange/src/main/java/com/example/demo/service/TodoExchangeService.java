package com.example.demo.service;

import com.example.demo.record.Todo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

@HttpExchange(url = "https://jsonplaceholder.typicode.com/", accept = "application/json")
public interface TodoExchangeService {

    @GetExchange("/todos")
    List<Todo> getAllTodos();

    @GetExchange("/todos/{id}")
    Todo getTodoById(@PathVariable Long id);

    @GetExchange("/todos?userId={userId}")
    List<Todo> getTodosByUserId(@PathVariable Long userId);

    @PostExchange("/todos")
    Todo createTodo(@RequestBody Todo todo);

    @PutExchange("/todos/{id}")
    Todo updateTodo(@PathVariable Long id, @RequestBody Todo todo);

    @DeleteExchange("/todos/{id}")
    void deleteTodo(@PathVariable Long id);
}
