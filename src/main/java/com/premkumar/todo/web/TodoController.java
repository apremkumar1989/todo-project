package com.premkumar.todo.web;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.premkumar.todo.service.Todo;
import com.premkumar.todo.service.Todo.TodoTask;

@Controller
public class TodoController {

	@Autowired
	JdbcTemplate jdbcTemplate;

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
		Todo todo = getTodoByIdFromDatabase(id);
		model.addAttribute("todo", todo);
		return "todo";
	}

	private List<Todo> getTodosFromDatabase() {
		System.out.println("getting todos from database...");
		String strSelect = "select id, name from todo";
		List<Todo> result = jdbcTemplate
				.query(strSelect,
						(rs, rowNum) -> new Todo(rs.getInt("id"), rs
								.getString("name")));
		return CollectionUtils.isEmpty(result) ? Collections.emptyList()
				: result;
	}

	private Todo getTodoByIdFromDatabase(int id) {
		System.out.println("getting todo by id from database...");
		Todo todo = null;

		String strSelect = "select id, name from todo where id=?";
		List<Todo> result = jdbcTemplate
				.query(strSelect,
						new PreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement ps)
									throws SQLException {
								ps.setInt(1, id);

							}
						},
						(rs, rowNum) -> new Todo(rs.getInt("id"), rs
								.getString("name")));
		if (CollectionUtils.isEmpty(result))
			return null;

		todo = result.get(0);

		strSelect = "select id, name,completed from todo_task where todo_id=?";
		List<TodoTask> task_results = jdbcTemplate.query(
				strSelect,
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, id);

					}
				},
				(rs, rowNum) -> new TodoTask(rs.getInt("id"), rs
						.getString("name"), rs.getBoolean("completed")));

		todo.setTasks(task_results);
		return todo;
	}
}
