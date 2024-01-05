package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UserDto {
	
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	@JsonFormat(pattern="yyyy/mm/dd")
	private Date dateOfBirth;
	
	private String phone;
	
	private String username;
	
	@Email
	private String email;
	
	@Size(min = 6, max = 40, message="Password length should be between 6 and 50 ")
    private String password;

	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public UserDto(String firstName, String middleName, String lastName, @JsonFormat(pattern="yyyy/mm/dd") Date dateOfBirth, String phone,
			String username, @Email String email,
			@Size(min = 6, max = 40, message = "Password length should be between 6 and 50 ") String password) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.phone = phone;
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


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getMiddleName() {
		return middleName;
	}


	public void setMiddleName(String middleName) {
		this.middleName = middleName;
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
	
}
