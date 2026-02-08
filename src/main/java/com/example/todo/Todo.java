package com.example.todo;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private boolean done = false;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected Todo() {}

    public Todo(String title) {
        this.title = title;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public boolean isDone() { return done; }
    public Instant getCreatedAt() { return createdAt; }

    public void setTitle(String title) { this.title = title; }
    public void setDone(boolean done) { this.done = done; }
}
