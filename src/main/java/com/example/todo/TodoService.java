package com.example.todo;

import com.example.todo.dto.TodoDtos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository repo;

    public TodoService(TodoRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public TodoDtos.TodoListResponse list() {
        List<Todo> todos = repo.findAll();

        List<TodoDtos.TodoItem> items = todos.stream()
                .map(t -> new TodoDtos.TodoItem(t.getId(), t.getTitle(), t.isDone()))
                .toList();

        int total = items.size();
        int done = (int) items.stream().filter(TodoDtos.TodoItem::done).count();
        int remaining = total - done;

        return new TodoDtos.TodoListResponse(items, new TodoDtos.Summary(total, done, remaining));
    }

    @Transactional
    public TodoDtos.TodoItem create(TodoDtos.CreateTodoRequest req) {
        if (req == null || req.title() == null || req.title().isBlank()) {
            throw new IllegalArgumentException("title is required");
        }
        Todo saved = repo.save(new Todo(req.title().trim()));
        return new TodoDtos.TodoItem(saved.getId(), saved.getTitle(), saved.isDone());
    }

    @Transactional
    public TodoDtos.TodoItem update(Long id, TodoDtos.UpdateTodoRequest req) {
        Todo todo = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("todo not found"));

        if (req.title() != null) {
            String title = req.title().trim();
            if (!title.isEmpty()) todo.setTitle(title);
        }
        if (req.done() != null) {
            todo.setDone(req.done());
        }

        return new TodoDtos.TodoItem(todo.getId(), todo.getTitle(), todo.isDone());
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
