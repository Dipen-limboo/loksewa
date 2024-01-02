package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

public class OptionsDto {
	
	private String optionName;
	
	private boolean Correct;

	public OptionsDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OptionsDto(String optionName, boolean isCorrect) {
		super();
		this.optionName = optionName;
		this.Correct = isCorrect;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public boolean isCorrect() {
		return Correct;
	}

	public void setIsCorrect(boolean isCorrect) {
		this.Correct = isCorrect;
	}
	
	
	
}
