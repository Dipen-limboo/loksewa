package com.example.loksewa.aayog.LoksewaAayog.payload.response;

public class PositionListDto {
	private String position;
	
	private int total_exams;
	
	private double average_exams;
	public PositionListDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PositionListDto(String position, int total_exams) {
		super();
		this.position = position;
		this.total_exams = total_exams;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getTotal_exams() {
		return total_exams;
	}

	public void setTotal_exams(int total_exams) {
		this.total_exams = total_exams;
	}

	public double getAverage_exams() {
		return average_exams;
	}

	public void setAverage_exams(double average_exams) {
		this.average_exams = average_exams;
	}
	
	
}
