package com.premkumar.todo.service;

import java.util.ArrayList;
import java.util.List;

public class Todo {

	public Integer id;
	private String name;
	private List<TodoTask> tasks = new ArrayList<Todo.TodoTask>();

	public Todo() {

	}

	public Todo(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public List<TodoTask> getTasks() {
		return tasks;
	}

	public void setTasks(List<TodoTask> tasks) {
		this.tasks = tasks;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static class TodoTask {
		private Integer id;
		private String name;
		private boolean completed = false;

		public TodoTask() {
		}

		public TodoTask(int id, String name, boolean completed) {
			this.id = id;
			this.name = name;
			this.completed = completed;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isCompleted() {
			return completed;
		}

		public void setCompleted(boolean completed) {
			this.completed = completed;
		}

	}
}
