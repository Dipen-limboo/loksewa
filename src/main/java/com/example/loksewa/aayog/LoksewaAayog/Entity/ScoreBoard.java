package com.example.loksewa.aayog.LoksewaAayog.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="test_answers")
public class ScoreBoard {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="question_id")
	private Question question;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="option_id",  nullable=true)
	private Option option;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="exams_id")
	private UserScore test;

	public ScoreBoard() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ScoreBoard(Long id, Question question, Option option, UserScore board) {
		super();
		this.id = id;
		this.question = question;
		this.option = option;
		this.test = board;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}

	public UserScore getBoard() {
		return test;
	}

	public void setBoard(UserScore board) {
		this.test = board;
	}
	
}
