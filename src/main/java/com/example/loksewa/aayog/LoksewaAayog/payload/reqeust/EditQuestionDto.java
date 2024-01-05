package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

import java.util.List;

public class EditQuestionDto {
	private int category;
	
	private Long position;
	
	private int year;
	
	private String question;
	
	private int optionType;
	
	private List<EditOptionDto> option;

	public EditQuestionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public EditQuestionDto(int category, Long position, int year, String question, int optionType,
			List<EditOptionDto> option) {
		super();
		this.category = category;
		this.position = position;
		this.year = year;
		this.question = question;
		this.optionType = optionType;
		this.option = option;
	}


	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getOptionType() {
		return optionType;
	}

	public void setOptionType(int optionType) {
		this.optionType = optionType;
	}

	public List<EditOptionDto> getOption() {
		return option;
	}

	public void setOption(List<EditOptionDto> option) {
		this.option = option;
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
}
