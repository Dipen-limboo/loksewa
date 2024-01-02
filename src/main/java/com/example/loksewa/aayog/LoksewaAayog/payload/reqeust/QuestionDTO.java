package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class QuestionDTO {

	
	@NotBlank
	private String questionText;
	
	@NotNull
	private int optionType;
	
	
	private List<OptionsDto> options;

	public QuestionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuestionDTO(@NotBlank String questionText, @NotNull int optionType, List<OptionsDto> options) {
		super();
		this.questionText = questionText;
		this.optionType = optionType;
		this.options = options;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public int getOptionType() {
		return optionType;
	}

	public void setOptionType(int optionType) {
		this.optionType = optionType;
	}

	public List<OptionsDto> getOptions() {
		return options;
	}

	public void setOptions(List<OptionsDto> options) {
		this.options = options;
	}



	
}
