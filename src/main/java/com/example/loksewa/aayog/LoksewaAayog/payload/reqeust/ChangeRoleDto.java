package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;

public class ChangeRoleDto {
	@NotEmpty
	private Set<String> role;

	public ChangeRoleDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChangeRoleDto(@NotEmpty Set<String> role) {
		super();
		this.role = role;
	}

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}
	
	
	
}
