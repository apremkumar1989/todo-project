package com.premkumar.todo.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		List<Todo> data = new ArrayList<Todo>();

		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/todo", "root", "");

			// Step 2: Allocate a 'Statement' object in the Connection
			stmt = conn.createStatement();
			String strSelect = "select id, name from todo";
			System.out.println("The SQL query is: " + strSelect); // Echo For
																	// debugging
			System.out.println();

			ResultSet rset = stmt.executeQuery(strSelect);

			// Step 4: Process the ResultSet by scrolling the cursor forward via
			// next().
			// For each row, retrieve the contents of the cells with
			// getXxx(columnName).
			System.out.println("The records selected are:");
			int rowCount = 0;
			while (rset.next()) { // Move the cursor to the next row, return
									// false if no more row
				Todo todo = new Todo(rset.getInt("id"), rset.getString("name"));
				++rowCount;
				data.add(todo);
			}
			System.out.println("Total number of records = " + rowCount);

			stmt.close();
			conn.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (Exception se) {
				se.printStackTrace();
			}
		}

		return data;
	}

	private Todo getTodoByIdFromDatabase(int id) {
		Todo data = null;

		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/todo", "root", "");

			// Step 2: Allocate a 'Statement' object in the Connection
			String strSelect = "SELECT t.id, t.name, t.user_id, tt.id as tt_id,tt.name as tt_name,tt.completed as tt_completed FROM todo t join todo_task tt on t.id=tt.todo_id where t.id=?";
			stmt = conn.prepareStatement(strSelect);
			stmt.setInt(1, id);
			System.out.println("The SQL query is: " + strSelect); // Echo For
																	// debugging
			System.out.println();

			ResultSet rset = stmt.executeQuery();

			// Step 4: Process the ResultSet by scrolling the cursor forward via
			// next().
			// For each row, retrieve the contents of the cells with
			// getXxx(columnName).
			System.out.println("The records selected are:");
			int rowCount = 0;
			Todo todo = null;
			while (rset.next()) { // Move the cursor to the next row, return
								// false if no more row
				if (todo == null)
					todo = new Todo(rset.getInt("id"), rset.getString("name"));
				todo.getTasks().add(
						new TodoTask(rset.getInt("tt_id"), rset
								.getString("tt_name"), rset
								.getBoolean("tt_completed")));
				++rowCount;
				data = todo;
			}
			System.out.println("Total number of records = " + rowCount);

			stmt.close();
			conn.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (Exception se) {
				se.printStackTrace();
			}
		}

		return data;
	}
}
