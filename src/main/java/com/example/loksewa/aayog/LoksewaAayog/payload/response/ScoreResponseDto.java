package com.example.loksewa.aayog.LoksewaAayog.payload.response;

public class ScoreResponseDto {
	private int total;
	private int right;
	
	public ScoreResponseDto(int total, int right) {
		super();
		this.total = total;
		this.right = right;
	}
	public ScoreResponseDto() {
		// TODO Auto-generated constructor stub
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getRight() {
		return right;
	}
	public void setRight(int right) {
		this.right = right;
	}
	
}
