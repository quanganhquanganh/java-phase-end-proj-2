package com.sportyshoes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.sportyshoes.model.Category;
import com.sportyshoes.model.Product;
import com.sportyshoes.repository.CategoryRepository;
import com.sportyshoes.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class InitializeProductsAndCategoriesSetup implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if categories exist, if not create them
        if (categoryRepository.count() == 0) {
            List<Category> categories = Arrays.asList(
                new Category("Running"),
                new Category("Basketball"),
                new Category("Tennis"),
                new Category("Football")
            );
            categoryRepository.saveAll(categories);
            System.out.println("Categories created");
        }

        // Check if products exist, if not create them
        if (productRepository.count() == 0) {
            Category running = categoryRepository.findByName("Running").orElseThrow();
            Category basketball = categoryRepository.findByName("Basketball").orElseThrow();
            Category tennis = categoryRepository.findByName("Tennis").orElseThrow();
            Category football = categoryRepository.findByName("Football").orElseThrow();

            List<Product> products = Arrays.asList(
                new Product("Sprint Pro", "Lightweight running shoes", new BigDecimal("99.99"), running),
                new Product("Air Dunk", "High-top basketball shoes", new BigDecimal("129.99"), basketball),
                new Product("Court Master", "Professional tennis shoes", new BigDecimal("89.99"), tennis),
                new Product("Striker Elite", "Soccer cleats for all terrains", new BigDecimal("109.99"), football)
            );
            productRepository.saveAll(products);
            System.out.println("Products created");
        }
    }
}
