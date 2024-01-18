package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;

public class ChangeRoleDto {
	
	private Set<String> role;

	private Set<String> status;
	public ChangeRoleDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChangeRoleDto(Set<String> role, Set<String> status) {
		super();
		this.role = role;
		this.status = status;
	}

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}

	public Set<String> getStatus() {
		return status;
	}

	public void setStatus(Set<String> status) {
		this.status = status;
	}
	
}
