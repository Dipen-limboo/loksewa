package com.example.loksewa.aayog.LoksewaAayog.payload.response;

public class RoleResponseDto {
	
	private long id;
	private String name;
	public RoleResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RoleResponseDto(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
