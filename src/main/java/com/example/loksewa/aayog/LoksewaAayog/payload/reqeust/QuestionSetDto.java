package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotNull;
@JsonIgnoreProperties({"questionText", "optionT", "options"})
public class QuestionSetDto {
	
	private int id;
	
	@NotNull
	private int position;
	
	private int year;
	
	private List<QuestionIdDto> questionId;

	public QuestionSetDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuestionSetDto(int id, @NotNull int position, int year, List<QuestionIdDto> questionId) {
		super();
		this.id = id;
		this.position = position;
		this.year = year;
		this.questionId = questionId;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<QuestionIdDto> getQuestionId() {
		return questionId;
	}

	public void setQuestionId(List<QuestionIdDto> questionId) {
		this.questionId = questionId;
	}
	
	
}
