package com.example.loksewa.aayog.LoksewaAayog.Entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
           @UniqueConstraint(columnNames = "email")
       })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(name="first_name")
  private String firstName;
  
  @Column(name="middle_name")
  private String middleName;
  
  @NotBlank
  @Column(name="last_name")
  private String lastName;
  
  @NotBlank
  @Size(max = 20)
  private String username;
  
  @NotBlank
  @Column(name="birth_date")
  private Date birthDate;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  @Pattern(regexp="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")
  private String password;
  
  @Transient
  private String ConfirmPassword;
  
  @Column(name= "phone")
  @Pattern(regexp="^(98|97)\\d{8}$",
  message="{The phone number must be number start with 97 or 98, it must contains 10 number}"
  )
  private String phone;
  
  
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", 
     joinColumns = @JoinColumn(name = "user_id"),
     inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();


  
  @Column(name="is_verified")
  private boolean isVerified =false;
  
  @Column(name="verified_date")
  @JsonFormat(pattern = "yyyy/MM/dd")
  private Date verifiedDate;
  
  @Column(name="verified_token")
  private String verifiedToken;
  
  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
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

  public String getPassword() {
	  return password;
  }

  public void setPassword(String password) {
	  this.password = password;
  }

  public Set<Role> getRoles() {
	  return roles;
  }

  public void setRoles(Set<Role> roles) {
	  this.roles = roles;
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

  public String getConfirmPassword() {
	  return ConfirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
	  ConfirmPassword = confirmPassword;
  }

  public String getPhone() {
	  return phone;
  }

  public void setPhone(String phone) {
	  this.phone = phone;
  }

  public Date getBirthDate() {
	  return birthDate;
  }

  public void setBirthDate(Date birthDate) {
	  this.birthDate = birthDate;
  }

  public boolean isVerified() {
	  return isVerified;
  }

  public void setVerified(boolean isVerified) {
	  this.isVerified = isVerified;
  }

  public Date getVerifiedDate() {
	  return verifiedDate;
  }

  public void setVerifiedDate(Date verifiedDate) {
	  this.verifiedDate = verifiedDate;
  }

  public String getVerifiedToken() {
	  return verifiedToken;
  }

  public void setVerifiedToken(String verifiedToken) {
	  this.verifiedToken = verifiedToken;
  }
}
