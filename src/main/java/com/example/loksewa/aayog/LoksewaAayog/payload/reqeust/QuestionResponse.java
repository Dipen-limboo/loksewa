package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

import java.util.Set;

import jakarta.validation.constraints.*;

public class QuestionResponse {

	@NotEmpty
	private Set<String> position;

	private int year; 
	
	@NotBlank
	private String questionText;
	
	@NotBlank
	private String optionA;
	
	@NotBlank
	private String optionB;
	
	@NotBlank
	private String optionC;
	
	@NotBlank
	private String optionD;
	
	@NotNull
	private int answer;
	
	public Set<String> getPosition() {
		return position;
	}

	public void setPosition(Set<String> position) {
		this.position = position;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	
	
}
