package com.example.loksewa.aayog.LoksewaAayog.payload.response;

public class GetListOfquestionSetDto {
	private String question;
	
	private String option;
	
	private String correctOption;

	public GetListOfquestionSetDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetListOfquestionSetDto(String question, String option, String correctOption) {
		super();
		this.question = question;
		this.option = option;
		this.correctOption = correctOption;
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
