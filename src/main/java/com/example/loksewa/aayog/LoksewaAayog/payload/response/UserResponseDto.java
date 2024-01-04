package com.example.loksewa.aayog.LoksewaAayog.payload.response;

import java.util.Set;

public class UserResponseDto {

	private Long id;
	
	private String username;
	
	private String email;
	
	private Set<RoleResponseDto> role;

	public UserResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserResponseDto(Long id, String username, String email, Set<RoleResponseDto>role) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<RoleResponseDto> getRole() {
		return role;
	}

	public void setRole(Set<RoleResponseDto> role) {
		this.role = role;
	}
	
	
}
