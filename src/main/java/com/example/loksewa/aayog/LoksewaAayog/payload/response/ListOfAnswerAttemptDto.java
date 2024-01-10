package com.example.loksewa.aayog.LoksewaAayog.payload.response;

import java.util.Date;
import java.util.List;

public class ListOfAnswerAttemptDto {
	private Long id;
	
	private Date date;
	
	private List<GetListOfquestionSetDto> listOfAnswer;

	public ListOfAnswerAttemptDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ListOfAnswerAttemptDto(Long id, List<GetListOfquestionSetDto> listOfAnswer) {
		super();
		this.id = id;
		this.listOfAnswer = listOfAnswer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<GetListOfquestionSetDto> getListOfAnswer() {
		return listOfAnswer;
	}

	public void setListOfAnswer(List<GetListOfquestionSetDto> listOfAnswer) {
		this.listOfAnswer = listOfAnswer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
