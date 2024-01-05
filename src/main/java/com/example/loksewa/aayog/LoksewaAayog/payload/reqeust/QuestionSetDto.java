package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotNull;
@JsonIgnoreProperties({"questionText", "optionT", "options"})
public class QuestionSetDto {
	
	private int id;
	
	@NotNull
	private Long category;
	
	@NotNull
	private int position;

	
	private List<QuestionIdDto> questionId;

	public QuestionSetDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuestionSetDto(int id, @NotNull Long category, @NotNull int position,  List<QuestionIdDto> questionId) {
		super();
		this.id = id;
		this.category=category;
		this.position = position;
		this.questionId = questionId;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
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

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}
	
}
