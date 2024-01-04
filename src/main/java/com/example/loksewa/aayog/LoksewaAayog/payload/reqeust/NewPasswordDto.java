package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

import jakarta.validation.constraints.NotEmpty;

public class NewPasswordDto {
	@NotEmpty
	private String token;
	
	@NotEmpty
	private String password;
	
	public NewPasswordDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NewPasswordDto(@NotEmpty String token, @NotEmpty String password) {
		super();
		this.token = token;
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
