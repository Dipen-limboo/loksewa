package com.example.loksewa.aayog.LoksewaAayog.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loksewa.aayog.LoksewaAayog.Entity.Option;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Question;

public interface OptionRepo extends JpaRepository<Option, Long> {
	List<Option> findByQuestion(Question question);
	Optional<Option> findById(Long optionsId);
}
