package com.example.loksewa.aayog.LoksewaAayog.payload.response;

public class CategoryListDto {
	private String category;
	
	private int total_exams;
	
	private double average;

	public CategoryListDto(String category, int total_exams) {
		super();
		this.category = category;
		this.total_exams = total_exams;
	}

	public CategoryListDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getTotal_exams() {
		return total_exams;
	}

	public void setTotal_exams(int total_exams) {
		this.total_exams = total_exams;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}
	
	
}
