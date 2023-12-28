package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

public class Answer {
	private Long id;
	private int option;
	
	
	public Answer(Long id, int option) {
		super();
		this.id = id;
		this.option = option;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getOption() {
		return option;
	}
	public void setOption(int option) {
		this.option = option;
	}
	
}
