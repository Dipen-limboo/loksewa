package com.example.loksewa.aayog.LoksewaAayog.payload.response;

public class GetListOfquestionSetDto {
	private String question;
	
	private Long option;
	
	private Long correctOption;

	public GetListOfquestionSetDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetListOfquestionSetDto(String question, Long option, Long correctOption) {
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

	public Long getOption() {
		return option;
	}

	public void setOption(Long option) {
		this.option = option;
	}

	public Long getCorrectOption() {
		return correctOption;
	}

	public void setCorrectOption(Long correctOption) {
		this.correctOption = correctOption;
	}
	
}
