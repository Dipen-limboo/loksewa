package com.example.loksewa.aayog.LoksewaAayog.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tests")
public class UserScore {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	@Column(name="test_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date date;
	
	@Column(name="expiry-date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date expiry;

	public UserScore() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserScore(Long id, User user, Date date, Date expiry) {
		super();
		this.id = id;
		this.user = user;
		this.date = date;
		this.expiry = expiry;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}
	
	
}