package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

public class EditOptionDto {
	private String option;
	private boolean check;
	public EditOptionDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EditOptionDto(String option, boolean check) {
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
