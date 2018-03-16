package com.premkumar.todo.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.premkumar.todo.entites.Todo;
import com.premkumar.todo.entites.TodoTask;
import com.premkumar.todo.repositories.TodoRepository;
import com.premkumar.todo.service.TodoDTO;
import com.premkumar.todo.service.TodoDTO.TodoTaskDTO;

@RestController
@RequestMapping("/api")
public class TodoApiController {

	@Autowired
	TodoRepository todoRepository;

	@GetMapping("/users/{userId}/todos")
	public List<TodoDTO> getTodosForUser(
			@PathParam(value = "userId") Integer userId) {
		// TODO: make it user specific
		return getTodosFromDatabase();
	}

	@GetMapping("/users/{userId}/todos/{todoId}")
	public TodoDTO getTodoByIdForUser(@PathVariable(value = "userId") Integer userId,
			@PathVariable(value = "todoId") Integer todoId) {
		// TODO: make it user specific
		return getTodoByIdFromDatabase(todoId);
	}

	private List<TodoDTO> getTodosFromDatabase() {
		System.out.println("get todos from database...");
		return todoRepository.findAll().stream().map(t -> {
			return mapToTaskDTO(t);
		}).collect(Collectors.toList());
	}

	private TodoDTO getTodoByIdFromDatabase(int id) {
		System.out.println("get todo by id from database...");
		Optional<Todo> findById = todoRepository.findById(id);
		return findById.isPresent() ? mapToTaskDTO(findById.get()) : null;
	}

	private TodoDTO mapToTaskDTO(Todo todo) {
		TodoDTO dto = new TodoDTO(todo.getId(), todo.getName());
		for (TodoTask task : todo.getTasks()) {
			dto.getTasks().add(
					new TodoTaskDTO(task.getId(), task.getName(), task
							.isCompleted()));
		}
		return dto;
	}
}
