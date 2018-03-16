package com.premkumar.todo.web;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.premkumar.todo.service.TodoDTO;
import com.premkumar.todo.service.TodoService;

@RestController
@RequestMapping("/api")
public class TodoApiController {

	@Autowired
	TodoService todoService;

	@GetMapping("/users/{userId}/todos")
	public List<TodoDTO> getTodosForUser(
			@PathParam(value = "userId") Integer userId) {
		// TODO: make it user specific
		return todoService.getTodosByUserId(userId);
	}

	@GetMapping("/users/{userId}/todos/{todoId}")
	public TodoDTO getTodoByIdForUser(@PathVariable(value = "userId") Integer userId,
			@PathVariable(value = "todoId") Integer todoId) {
		// TODO: make it user specific
		return todoService.getTodosByIdAndUserId(userId, todoId);
	}

	
}
