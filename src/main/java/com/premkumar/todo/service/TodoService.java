package com.premkumar.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.premkumar.todo.entites.Todo;
import com.premkumar.todo.entites.TodoTask;
import com.premkumar.todo.repositories.TodoRepository;
import com.premkumar.todo.service.TodoDTO.TodoTaskDTO;

@Service
public class TodoService {

	@Autowired
	TodoRepository todoRepository;

	public List<TodoDTO> getTodosByUserId(Integer userId) {
		// TODO: need to fetch at user level
		return getTodosFromDatabase();
	}

	public TodoDTO getTodosByIdAndUserId(Integer userId, Integer todoId) {
		// TODO: need to fetch at user level
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

	public void save(TodoDTO todoDto, Integer userId) {
		Todo todo = new Todo();
		todo.setUserId(userId);
		todo.setName(todoDto.getName());
		List<TodoTask> todo_tasks = new ArrayList<TodoTask>(0);
		todo.setTasks(todo_tasks);
		todoRepository.save(todo);
		
	}
}
