package com.example.loksewa.aayog.LoksewaAayog.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.loksewa.aayog.LoksewaAayog.Entity.UserScore;

import jakarta.persistence.criteria.Join;

public class ExamSpecification {

    public ExamSpecification() {
        super();
    }

    public static Specification<UserScore> getByYears(int years) {
        return (root, query, builder) -> builder.equal(root.get("questionSet").get("year"), years);
    }

    public static Specification<UserScore> getByUser(Long userId) {
        return (root, query, builder) -> userId == null ? builder.conjunction() : builder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<UserScore> getByCategories(Long categoryId) {
        return (root, query, builder) -> builder.equal(root.get("questionSet").get("category").get("id"), categoryId);
    }

    public static Specification<UserScore> getByPosition(long positionId) {
        return (root, query, builder) -> builder.equal(root.get("questionSet").get("position").get("id"), positionId);
    }
}
