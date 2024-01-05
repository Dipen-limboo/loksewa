package com.example.loksewa.aayog.LoksewaAayog.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loksewa.aayog.LoksewaAayog.Entity.Category;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Position;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Question;

public interface QuestionRepo extends JpaRepository<Question, Long>{
	List<Question> findByIdIn(List<Long> ids);
	
	Optional<Question> findById(Long id);
	


	List<Question> findByPositionAndCategory(Position position, Category cate);
}
