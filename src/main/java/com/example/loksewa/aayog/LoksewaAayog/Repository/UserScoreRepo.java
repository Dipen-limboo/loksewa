package com.example.loksewa.aayog.LoksewaAayog.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loksewa.aayog.LoksewaAayog.Entity.Question;
import com.example.loksewa.aayog.LoksewaAayog.Entity.User;
import com.example.loksewa.aayog.LoksewaAayog.Entity.UserScore;

public interface UserScoreRepo extends JpaRepository<UserScore, Long>{

}
