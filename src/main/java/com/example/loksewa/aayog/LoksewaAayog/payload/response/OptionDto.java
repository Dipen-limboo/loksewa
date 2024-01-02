package com.example.loksewa.aayog.LoksewaAayog.payload.response;

public class OptionDto {
	private String option;
	
	private boolean check;

	
	
	public OptionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OptionDto(String option, boolean check) {
		super();
		this.option = option;
		this.check = check;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}
	
	
}
