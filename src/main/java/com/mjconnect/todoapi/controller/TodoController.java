package com.mjconnect.todoapi.controller;

import com.mjconnect.todoapi.dto.PageDto;
import com.mjconnect.todoapi.dto.TodoDto;
import com.mjconnect.todoapi.entity.Todo;
import com.mjconnect.todoapi.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> getAllTodos(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "id,desc") String[] sort) {
        // Sort 파라미터 처리 (예: "title,asc" 또는 "completed,desc")
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Sort sortBy = Sort.by(direction, sort[0]);

        Pageable pageable = PageRequest.of(page, size, sortBy);
        Page<Todo> todoPage = todoService.getAllTodos(pageable);
        // Todo 엔티티를 TodoDto로 변환
        List<TodoDto> todos = todoPage.getContent().stream()
                .map(TodoDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageDto<>(todos, todoPage.getNumber(), todoPage.getSize(), todoPage.getTotalElements(), todoPage.getTotalPages()));
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
