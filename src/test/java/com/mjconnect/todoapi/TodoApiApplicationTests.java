package com.mjconnect.todoapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjconnect.todoapi.dto.PageDto;
import com.mjconnect.todoapi.dto.TodoDto;
import com.mjconnect.todoapi.entity.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class TodoApiApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    public void testTodoDtoSerialization() throws JsonProcessingException {
        List<TodoDto> todos = new ArrayList<>();
        todos.add(new TodoDto(1L, "Test Todo 1", false));
        todos.add(new TodoDto(2L, "Test Todo 2", true));

        PageDto<TodoDto> pageDto = new PageDto<>(todos, 0, 10, 2L, 1);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(pageDto);

        System.out.println(json);
    }
}
