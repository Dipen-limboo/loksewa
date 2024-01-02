package com.example.loksewa.aayog.LoksewaAayog.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="options")
public class Option {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Question question;
	
	@Column(name="option_text")
	private String text;
	
	@Column(name="correct_answer")
	private boolean isCorrect;

	public Option() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Option(long id, Question question, String text, boolean isTrue) {
		super();
		this.id = id;
		this.question = question;
		this.text = text;
		this.isCorrect = isTrue;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getText() {
		return text;
	}

	public void setText(String string) {
		this.text = string;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	
}
