package com.example.loksewa.aayog.LoksewaAayog.payload.response;

import java.util.List;

public class ScoreResponseDto {
	
	private String Message;
	private List<QuestionResponseDto> Questions;
	private int total;
	private int right;
	public ScoreResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ScoreResponseDto(List<QuestionResponseDto> listResponseDto, int total, int right) {
		super();
		this.Questions = listResponseDto;
		this.total = total;
		this.right = right;
	}
	public List<QuestionResponseDto> getListResponseDto() {
		return Questions;
	}
	public void setListResponseDto(List<QuestionResponseDto> listResponseDto) {
		this.Questions = listResponseDto;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getRight() {
		return right;
	}
	public void setRight(int right) {
		this.right = right;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	

	
}
