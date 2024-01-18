package com.example.loksewa.aayog.LoksewaAayog.payload.response;


public class YearDto {
	private int year;
	
	private int total_exams;
	
	private double avg;

	public YearDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public YearDto(int year, int total_exams, double avg) {
		super();
		this.year = year;
		this.total_exams = total_exams;
		this.avg = avg;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getTotal_exams() {
		return total_exams;
	}

	public void setTotal_exams(int total_exams) {
		this.total_exams = total_exams;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}
	
	
}
