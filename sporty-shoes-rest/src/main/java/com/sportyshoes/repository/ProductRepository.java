package com.sportyshoes.repository;

import com.sportyshoes.model.Product;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Collection<Product> findByCategoryId(Long categoryId);
	
	@Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
	Collection<Product> searchProducts(@Param("searchTerm") String searchTerm);
}
