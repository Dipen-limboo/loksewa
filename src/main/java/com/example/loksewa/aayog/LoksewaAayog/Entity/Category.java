package com.example.loksewa.aayog.LoksewaAayog.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name="categories")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="category_name")
	private String name;

	public Category() {
		super();
	}

	public Category(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
