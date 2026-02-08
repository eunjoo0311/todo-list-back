package com.example.todo.dto;

import java.util.List;

public class TodoDtos {

    public record CreateTodoRequest(String title) {}
    public record UpdateTodoRequest(String title, Boolean done) {}

    public record TodoItem(Long id, String title, boolean done) {}

    public record Summary(int total, int done, int remaining) {}

    public record TodoListResponse(List<TodoItem> items, Summary summary) {}
}
