package com.mjconnect.todoapi.controller;

import com.mjconnect.todoapi.entity.Todo;
import com.mjconnect.todoapi.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

//    •	목록 조회: GET http://localhost:8080/api/todos
//    •	특정 To-Do 조회: GET http://localhost:8080/api/todos/{id}
//    •	To-Do 생성: POST http://localhost:8080/api/todos (Body: {"title": "New Task", "completed": false})
//    •	To-Do 업데이트: PUT http://localhost:8080/api/todos/{id} (Body: {"title": "Updated Task", "completed": true})
//    •	To-Do 삭제: DELETE http://localhost:8080/api/todos/{id}


    // GET /api/todos - Get all todos
    @GetMapping
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    // GET /api/todos/{id} - Get a todo by id
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Todo todo = todoService.getTodoById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
        return ResponseEntity.ok(todo);
    }

    // POST /api/todos - Create a new todo
    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return todoService.createTodo(todo);
    }

    // PUT /api/todos/{id} - Update a todo
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
        Todo updatedTodo = todoService.updateTodo(id, todoDetails);
        return ResponseEntity.ok(updatedTodo);
    }

    // DELETE /api/todos/{id} - Delete a todo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok().build();
    }
}
