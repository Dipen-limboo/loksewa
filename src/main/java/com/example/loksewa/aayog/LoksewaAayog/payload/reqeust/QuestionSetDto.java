package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
@JsonIgnoreProperties({"questionText", "optionT", "options"})
public class QuestionSetDto {
	
	private int id;
	
	@NotNull
	private Long category;
	
	@NotNull
	private int position;
	
	
	private int year;

	private Set<String> setOption;
	
	private List<QuestionIdDto> questionId;

	public QuestionSetDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuestionSetDto(int id, @NotNull Long category, @NotNull int position, Set<String> setOption, List<QuestionIdDto> questionId) {
		super();
		this.id = id;
		this.category=category;
		this.position = position;
		this.setOption = setOption;
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

	public Set<String> getSetOption() {
		return setOption;
	}

	public void setSetOption(Set<String> setOption) {
		this.setOption = setOption;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
}
