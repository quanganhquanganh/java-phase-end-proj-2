package com.sportyshoes.repository;

import com.sportyshoes.model.Product;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Collection<Product> findByCategoryId(Long categoryId);
}
