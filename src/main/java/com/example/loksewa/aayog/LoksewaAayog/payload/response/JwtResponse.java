package com.example.loksewa.aayog.LoksewaAayog.payload.response;

import java.util.Date;
import java.util.List;

public class JwtResponse {
	private String token;
	private Long id;
	private String firstname;
	private String middlename;
	private String lastname;
	private Date dateOfbirth;
	private String phone;
	private String username;
	private String email;
	private List<String> roles;


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	public JwtResponse(String token, Long id, String firstname, String middlename, String lastname, Date dateOfbirth,
			String phone, String username, String email, List<String> roles) {
		super();
		this.token = token;
		this.id = id;
		this.firstname = firstname;
		this.middlename = middlename;
		this.lastname = lastname;
		this.dateOfbirth = dateOfbirth;
		this.phone = phone;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getDateOfbirth() {
		return dateOfbirth;
	}

	public void setDateOfbirth(Date dateOfbirth) {
		this.dateOfbirth = dateOfbirth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
}