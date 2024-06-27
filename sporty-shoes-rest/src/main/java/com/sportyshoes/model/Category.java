package com.sportyshoes.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    @OneToMany(mappedBy = "category")
    private List<Product> products;
    
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}