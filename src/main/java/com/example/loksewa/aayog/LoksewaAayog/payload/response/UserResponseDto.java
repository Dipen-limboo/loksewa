package com.example.loksewa.aayog.LoksewaAayog.payload.response;

import java.util.Date;
import java.util.Set;

public class UserResponseDto {

	private Long id;
	
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	private Date dateOfBirth;
	
	private String phone;
	
	private String username;
	
	private String email;
	
	private Set<RoleResponseDto> role;

	public UserResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public UserResponseDto(Long id, String firstName, String middleName, String lastName, Date dateOfBirth, String phone, String username,
			String email, Set<RoleResponseDto> role) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.phone = phone;
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


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Date getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getMiddleName() {
		return middleName;
	}


	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	
}
