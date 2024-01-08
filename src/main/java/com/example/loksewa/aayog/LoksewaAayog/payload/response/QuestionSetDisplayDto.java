package com.example.loksewa.aayog.LoksewaAayog.payload.response;

import java.util.List;

public class QuestionSetDisplayDto {
	private Long id;
	
	private List<DisplayQuestionSetDto> quesitons;

	public QuestionSetDisplayDto(Long id, List<DisplayQuestionSetDto> quesitons) {
		super();
		this.id = id;
		this.quesitons = quesitons;
	}

	public QuestionSetDisplayDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<DisplayQuestionSetDto> getQuesitons() {
		return quesitons;
	}

	public void setQuesitons(List<DisplayQuestionSetDto> quesitons) {
		this.quesitons = quesitons;
	}
	
	
}
