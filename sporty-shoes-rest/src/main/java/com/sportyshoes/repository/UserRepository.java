package com.sportyshoes.repository;

import com.sportyshoes.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	List<User> findByUsernameContaining(String searchTerm);
}
