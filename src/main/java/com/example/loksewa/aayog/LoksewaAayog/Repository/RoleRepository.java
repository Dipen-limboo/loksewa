package com.example.loksewa.aayog.LoksewaAayog.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.loksewa.aayog.LoksewaAayog.Entity.ERole;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}