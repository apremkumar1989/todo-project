package com.premkumar.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.premkumar.todo.entites.User;

public interface UserRepository extends JpaRepository<User, Integer>,
		JpaSpecificationExecutor<User> {

	User findByUsername(String username);
}
