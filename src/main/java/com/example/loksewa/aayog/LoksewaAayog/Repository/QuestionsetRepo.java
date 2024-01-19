package com.example.loksewa.aayog.LoksewaAayog.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.loksewa.aayog.LoksewaAayog.Entity.Category;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Position;
import com.example.loksewa.aayog.LoksewaAayog.Entity.QuestionSet;

public interface QuestionsetRepo extends JpaRepository<QuestionSet, Long>, JpaSpecificationExecutor<QuestionSet>{

	List<QuestionSet> findByCategoryAndPosition(Category cate, Position position);

	List<QuestionSet> findByCategory(Category cate);

	List<QuestionSet> findByPosition(Position position);

	List<QuestionSet> findByYear(int year);

}
