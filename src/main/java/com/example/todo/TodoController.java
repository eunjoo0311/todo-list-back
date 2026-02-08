package com.example.todo;

import com.example.todo.dto.TodoDtos;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public TodoDtos.TodoListResponse list() {
        return service.list();
    }

    @PostMapping
    public TodoDtos.TodoItem create(@RequestBody TodoDtos.CreateTodoRequest req) {
        return service.create(req);
    }

    @PatchMapping("/{id}")
    public TodoDtos.TodoItem update(@PathVariable Long id, @RequestBody TodoDtos.UpdateTodoRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
