package com.example.loksewa.aayog.LoksewaAayog.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loksewa.aayog.LoksewaAayog.Entity.UserScore;

public interface ScoreRepo extends JpaRepository<UserScore, Long>{

}
