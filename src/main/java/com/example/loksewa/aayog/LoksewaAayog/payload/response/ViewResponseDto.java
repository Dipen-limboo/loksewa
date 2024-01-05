package com.example.loksewa.aayog.LoksewaAayog.payload.response;

import java.util.List;

public class ViewResponseDto {

	private Long id;
	
	private String question;
	
	private int category;
	
	private Long position;
	
	private int year;
	
	
	private Long OptionType;
	
	private List<OptionDto> optionResponse;

	
	public ViewResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public ViewResponseDto(Long id, String question, int category, Long position, int year, Long optionType,
			List<OptionDto> optionResponse) {
		super();
		this.id = id;
		this.question = question;
		this.category = category;
		this.position = position;
		this.year = year;
		OptionType = optionType;
		this.optionResponse = optionResponse;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Long getOptionType() {
		return OptionType;
	}

	public void setOptionType(Long optionType) {
		OptionType = optionType;
	}

	public List<OptionDto> getOptionResponse() {
		return optionResponse;
	}

	public void setOptionResponse(List<OptionDto> optionResponse) {
		this.optionResponse = optionResponse;
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
