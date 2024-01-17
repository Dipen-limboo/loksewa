package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

public class DashBoardDto {
	private int total_exams;
	
	private double averageMarks;

	public DashBoardDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DashBoardDto(int total_exams, double averageMarks) {
		super();
		this.total_exams = total_exams;
		this.averageMarks = averageMarks;
	}

	public int getTotal_exams() {
		return total_exams;
	}

	public void setTotal_exams(int total_exams) {
		this.total_exams = total_exams;
	}

	public double getAverageMarks() {
		return averageMarks;
	}

	public void setAverageMarks(double averageMarks) {
		this.averageMarks = averageMarks;
	}
	
	
}
