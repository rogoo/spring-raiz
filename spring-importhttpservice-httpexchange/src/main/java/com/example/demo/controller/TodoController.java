package com.example.demo.controller;

import com.example.demo.record.Todo;
import com.example.demo.service.TodoExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoExchangeService todoExchangeService;

    public TodoController(TodoExchangeService todoExchangeService) {
        this.todoExchangeService = todoExchangeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Todo>> getTodos() {
        List<Todo> allTodos = todoExchangeService.getAllTodos();
        return ResponseEntity.ok(allTodos);
    }
}
