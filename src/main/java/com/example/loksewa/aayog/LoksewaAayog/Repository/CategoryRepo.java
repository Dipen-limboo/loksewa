package com.example.loksewa.aayog.LoksewaAayog.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.loksewa.aayog.LoksewaAayog.Entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category>{

}
