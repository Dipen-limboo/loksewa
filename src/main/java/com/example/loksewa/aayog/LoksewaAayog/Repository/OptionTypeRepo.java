package com.example.loksewa.aayog.LoksewaAayog.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loksewa.aayog.LoksewaAayog.Entity.OptionType;

public interface OptionTypeRepo extends JpaRepository<OptionType, Long>{
	OptionType findById(int id);
}
