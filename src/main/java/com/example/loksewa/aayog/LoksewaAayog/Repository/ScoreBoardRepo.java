package com.example.loksewa.aayog.LoksewaAayog.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loksewa.aayog.LoksewaAayog.Entity.ScoreBoard;
import com.example.loksewa.aayog.LoksewaAayog.Entity.UserScore;

public interface ScoreBoardRepo extends JpaRepository<ScoreBoard, Long>{

	List<ScoreBoard> findByTest(UserScore score);

}
