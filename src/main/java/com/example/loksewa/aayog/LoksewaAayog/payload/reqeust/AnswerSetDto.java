package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

import java.util.List;

public class AnswerSetDto {
	private List<AnswerDto> answer;

	public AnswerSetDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AnswerSetDto(List<AnswerDto> answer) {
		super();
		this.answer = answer;
	}

	public List<AnswerDto> getAnswer() {
		return answer;
	}

	public void setAnswer(List<AnswerDto> answer) {
		this.answer = answer;
	}
	
	
}
