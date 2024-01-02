package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

public class EditOptionDto {
	private long id;
	private String option;
	private boolean check;
	public EditOptionDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EditOptionDto(long id,String option, boolean check) {
		super();
		this.id = id;
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
}
