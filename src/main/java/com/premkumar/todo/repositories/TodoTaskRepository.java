package com.premkumar.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.premkumar.todo.entites.TodoTask;

public interface TodoTaskRepository extends JpaRepository<TodoTask, Integer>,
		JpaSpecificationExecutor<TodoTask> {

}
