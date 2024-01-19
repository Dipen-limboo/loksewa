package com.example.loksewa.aayog.LoksewaAayog.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.loksewa.aayog.LoksewaAayog.Entity.Position;

public interface PositionRepo extends JpaRepository<Position, Long>, JpaSpecificationExecutor<Position>{
	Optional<Position> findById(int positionId);
}
