package com.example.loksewa.aayog.LoksewaAayog.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class QuestionSetoption {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SetOption name;

    public QuestionSetoption() {

    }

    public QuestionSetoption(SetOption name) {
      this.name = name;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SetOption getName() {
		return name;
	}

	public void setName(SetOption name) {
		this.name = name;
	}
}
