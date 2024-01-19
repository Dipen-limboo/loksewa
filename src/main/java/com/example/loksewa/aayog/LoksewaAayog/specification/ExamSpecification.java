package com.example.loksewa.aayog.LoksewaAayog.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.loksewa.aayog.LoksewaAayog.Entity.QuestionSet;
import com.example.loksewa.aayog.LoksewaAayog.Entity.UserScore;

import jakarta.persistence.criteria.Join;

public class ExamSpecification {

	public ExamSpecification() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static Specification<UserScore> getByYears(int years){
		return (root, query, builder)->{
			Join<QuestionSet, UserScore> userScoreQuestionSet = root.join("userScore");
			return builder.equal(userScoreQuestionSet.get("year"), years);
		};
	}
	
	public static Specification<UserScore> getByuser(Long userId){
		return (root, query, builder) -> userId == null ? builder.conjunction() : builder.equal(root.get("user"), userId);
	}
	
	public static Specification<UserScore> getByCategories(Long categoryId){
		return(root, query, builder) -> {
			Join<QuestionSet, UserScore> userScoreSet = root.join("userScore");
			return builder.equal(userScoreSet.get("category"), categoryId);
		};
	}
	
	public static Specification<UserScore> getByPosition(long positionId){
		return (root, query, builder) -> {
			Join<QuestionSet, UserScore> userScoreQuestion = root.join("userScore");
			return builder.equal(userScoreQuestion.get("position"), positionId);
		};
	}
}
