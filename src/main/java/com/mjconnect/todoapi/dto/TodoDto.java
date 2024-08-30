package com.mjconnect.todoapi.dto;

import com.mjconnect.todoapi.entity.Todo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TodoDto {
    private Long id;
    private String title;
    private boolean completed;

    // 생성자
    public TodoDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.completed = todo.getCompleted();
    }

    public TodoDto(Long id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }
}
