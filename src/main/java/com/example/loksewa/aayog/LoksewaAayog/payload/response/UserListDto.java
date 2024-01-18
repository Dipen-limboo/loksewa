package com.example.loksewa.aayog.LoksewaAayog.payload.response;

public class UserListDto {
	private String username;
	
	private int total_exams;
	
	private double average_marks;

	public UserListDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserListDto(String username, int total_exams, double average_marks) {
		super();
		this.username = username;
		this.total_exams = total_exams;
		this.average_marks = average_marks;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getTotal_exams() {
		return total_exams;
	}

	public void setTotal_exams(int total_exams) {
		this.total_exams = total_exams;
	}

	public double getAverage_marks() {
		return average_marks;
	}

	public void setAverage_marks(double average_marks) {
		this.average_marks = average_marks;
	}
	
	
}
