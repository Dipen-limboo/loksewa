package com.example.loksewa.aayog.LoksewaAayog.payload.reqeust;

import jakarta.validation.constraints.NotNull;

public class AnswerDto {
	@NotNull
	private Long questionId;
	
	@NotNull
	private Long optionId;

	public AnswerDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AnswerDto(@NotNull Long questionId, @NotNull Long optionId) {
		super();
		this.questionId = questionId;
		this.optionId = optionId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getOptionId() {
		return optionId;
	}

	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}
	
}
