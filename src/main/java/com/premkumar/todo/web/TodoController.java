package com.premkumar.todo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.premkumar.todo.service.Todo;
import com.premkumar.todo.service.Todo.TodoTask;

@Controller
public class TodoController {

	@GetMapping("/greeting")
	public String greeting(
			@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	@GetMapping("/todos")
	public String todos(Model model) {
		model.addAttribute("todos", getDummyTodos());
		return "todos";
	}

	private List<Todo> getDummyTodos() {
		List<Todo> todos = new ArrayList<Todo>();
		Todo todo = new Todo();
		todo.setId(1);
		todo.setName("Travel");
		todo.getTasks().add(new TodoTask(1, "Backpack", false));
		todo.getTasks().add(new TodoTask(2, "Shoes", false));
		todo.getTasks().add(new TodoTask(3, "Eatables", false));
		todo.getTasks().add(new TodoTask(4, "Tickets", false));
		todos.add(todo);

		todo = new Todo();
		todo.setId(2);
		todo.setName("Grocery");
		todo.getTasks().add(new TodoTask(5, "Olive Oil", false));
		todo.getTasks().add(new TodoTask(6, "Peanuts", false));
		todo.getTasks().add(new TodoTask(7, "Chips", false));
		todos.add(todo);

		return todos;
	}

	@GetMapping("/todo/{id}")
	public String todo(Model model) {
		Todo todo = new Todo();
		todo.setId(1);
		todo.setName("Travel");
		todo.getTasks().add(new TodoTask(1, "Backpack", false));
		todo.getTasks().add(new TodoTask(2, "Shoes", false));
		todo.getTasks().add(new TodoTask(3, "Eatables", false));
		todo.getTasks().add(new TodoTask(4, "Tickets", false));
		model.addAttribute("todo", todo);
		return "todo";
	}
}
