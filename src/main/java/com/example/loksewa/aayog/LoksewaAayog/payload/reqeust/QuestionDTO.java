package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class QuestionDTO {

	private int category;
	
	private Long position;
	
	private int year;
	
	@NotBlank
	private String questionText;
	
	@NotNull
	private int optionType;
	
	
	private List<OptionsDto> options;

	public QuestionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public QuestionDTO(int category, Long position, int year, @NotBlank String questionText, @NotNull int optionType,
			List<OptionsDto> options) {
		super();
		this.category = category;
		this.position = position;
		this.year = year;
		this.questionText = questionText;
		this.optionType = optionType;
		this.options = options;
	}


	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getOptionType() {
		return optionType;
	}

	public void setOptionType(int optionType) {
		this.optionType = optionType;
	}

	public List<OptionsDto> getOptions() {
		return options;
	}

	public void setOptions(List<OptionsDto> options) {
		this.options = options;
	}

	
}
