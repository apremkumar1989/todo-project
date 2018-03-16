package com.premkumar.todo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.premkumar.todo.security.CustomUserDetails;
import com.premkumar.todo.service.TodoDTO;
import com.premkumar.todo.service.TodoService;

@Controller
public class TodoController {

	@Autowired
	TodoService todoService;

	@GetMapping("/")
	public String home() {
		return "redirect:/todos";
	}

	@GetMapping("/greeting")
	public String greeting(
			@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	@GetMapping("/todos")
	public String todos(@ModelAttribute("userId") Integer userId, Model model) {
		model.addAttribute("todos", todoService.getTodosByUserId(userId));
		return "todos";
	}

	@GetMapping("/todos/{id}")
	public String todo(@PathVariable int id,
			@ModelAttribute("userId") Integer userId, Model model) {
		TodoDTO todo = todoService.getTodosByIdAndUserId(userId, id);
		model.addAttribute("todo", todo);
		return "todo";
	}

	@ModelAttribute("userId")
	public Integer getUserId(Authentication authentication) {
		CustomUserDetails customUser = (CustomUserDetails) authentication
				.getPrincipal();
		System.out.println("current logged in user id: "
				+ customUser.getUserId());
		return customUser.getUserId();
	}
}
