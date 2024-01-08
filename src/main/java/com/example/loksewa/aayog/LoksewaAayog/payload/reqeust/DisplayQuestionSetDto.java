package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

import java.util.List;

public class DisplayQuestionSetDto {
	private Long id;
	
	private String question;
	
	private Long OptionType;
	
	private List<DisplayOptionDto> option;

	public DisplayQuestionSetDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DisplayQuestionSetDto(Long id,String question, Long optionType, List<DisplayOptionDto> option) {
		super();
		this.id = id;
		this.question = question;
		OptionType = optionType;
		this.option = option;
	}

	public String getQuestion() {
		return question;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<DisplayOptionDto> getOption() {
		return option;
	}

	public void setOption(List<DisplayOptionDto> option) {
		this.option = option;
	}
	
	
}
