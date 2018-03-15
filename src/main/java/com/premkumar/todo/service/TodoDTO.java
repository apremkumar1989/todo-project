package com.premkumar.todo.service;

import java.util.ArrayList;
import java.util.List;

public class TodoDTO {

	public Integer id;
	private String name;
	private List<TodoTaskDTO> tasks = new ArrayList<TodoDTO.TodoTaskDTO>();

	public TodoDTO() {

	}

	public TodoDTO(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public List<TodoTaskDTO> getTasks() {
		return tasks;
	}

	public void setTasks(List<TodoTaskDTO> tasks) {
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

	public static class TodoTaskDTO {
		private Integer id;
		private String name;
		private boolean completed = false;

		public TodoTaskDTO() {
		}

		public TodoTaskDTO(int id, String name, boolean completed) {
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
