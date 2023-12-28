package com.example.loksewa.aayog.LoksewaAayog.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loksewa.aayog.LoksewaAayog.Entity.Question;

public interface QuestionRepo extends JpaRepository<Question, Long>{
	List<Question> findByIdIn(List<Long> ids);
	
	Long countByIdAndAnswer(int id, int Answer);
}
