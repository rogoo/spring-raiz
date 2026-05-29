package com.example.demo.record;

public record Todo(Long id, Long userId, String title, Boolean completed) {
}
