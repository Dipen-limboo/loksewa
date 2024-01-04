package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UserDto {
	private String username;
	
	@Email
	private String email;
	
	@Size(min = 6, max = 40, message="Password length should be between 6 and 50 ")
    private String password;

	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserDto(String username, @Email String email,
			@Size(min = 6, max = 40, message = "Password length should be between 6 and 50 ") String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
