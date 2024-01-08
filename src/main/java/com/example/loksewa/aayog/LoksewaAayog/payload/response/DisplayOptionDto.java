package com.example.loksewa.aayog.LoksewaAayog.payload.response;

public class DisplayOptionDto {
	private long id;
	
	private String text;

	public DisplayOptionDto(long id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public DisplayOptionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
