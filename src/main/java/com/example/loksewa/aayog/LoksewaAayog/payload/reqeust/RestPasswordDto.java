package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class RestPasswordDto {
	@NotEmpty
	@Email
	private String email;

	public RestPasswordDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public RestPasswordDto(@NotEmpty @Email String email) {
		super();
		this.email = email;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
