package com.example.loksewa.aayog.LoksewaAayog.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loksewa.aayog.LoksewaAayog.Entity.ResetPassword;

public interface ResetpasswordRepo extends JpaRepository<ResetPassword, Long>{
	ResetPassword findByToken(String token); 
}
