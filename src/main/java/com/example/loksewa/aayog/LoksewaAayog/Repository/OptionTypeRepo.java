package com.example.loksewa.aayog.LoksewaAayog.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.loksewa.aayog.LoksewaAayog.Entity.OptionType;

public interface OptionTypeRepo extends JpaRepository<OptionType, Long>, JpaSpecificationExecutor<OptionType>{
	OptionType findById(int id);
}
