package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

import java.util.List;

public class EditQuestionDto {
	private String question;
	
	private int optionType;
	
	private List<EditOptionDto> option;

	public EditQuestionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EditQuestionDto(String question, int optionType, List<EditOptionDto> option) {
		super();
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
	
	
}
