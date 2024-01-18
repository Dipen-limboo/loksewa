package com.example.loksewa.aayog.LoksewaAayog.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loksewa.aayog.LoksewaAayog.Entity.Category;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Position;
import com.example.loksewa.aayog.LoksewaAayog.Entity.QuestionSet;

public interface QuestionsetRepo extends JpaRepository<QuestionSet, Long>{

	List<QuestionSet> findByCategoryAndPosition(Category cate, Position position);

	List<QuestionSet> findByCategory(Category cate);

}
