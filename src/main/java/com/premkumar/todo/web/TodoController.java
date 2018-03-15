package com.premkumar.todo.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.premkumar.todo.entites.Todo;
import com.premkumar.todo.entites.TodoTask;
import com.premkumar.todo.repositories.TodoRepository;
import com.premkumar.todo.service.TodoDTO;
import com.premkumar.todo.service.TodoDTO.TodoTaskDTO;

@Controller
public class TodoController {

	@Autowired
	TodoRepository todoRepository;

	@GetMapping("/greeting")
	public String greeting(
			@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	@GetMapping("/todos")
	public String todos(Model model) {
		model.addAttribute("todos", getTodosFromDatabase());
		return "todos";
	}

	@GetMapping("/todos/{id}")
	public String todo(@PathVariable int id, Model model) {
		TodoDTO todo = getTodoByIdFromDatabase(id);
		model.addAttribute("todo", todo);
		return "todo";
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
