package com.example.loksewa.aayog.LoksewaAayog.payload.response;

public class QuestionResponseDto {
	private String question;
	
	private String option;
	
	private String correctOption;

	public QuestionResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuestionResponseDto(String question, String option) {
		super();
		this.question = question;
		this.option = option;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getCorrectOption() {
		return correctOption;
	}

	public void setCorrectOption(String correctOption) {
		this.correctOption = correctOption;
	}
	
	
}
