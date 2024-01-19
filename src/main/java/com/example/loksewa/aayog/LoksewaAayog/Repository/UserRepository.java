package com.example.loksewa.aayog.LoksewaAayog.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.loksewa.aayog.LoksewaAayog.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  @Query("SELECT u from User u Where u.email =:email")
  Optional<User> findByEmail(@Param("email") String email);
  
  Optional<User> findById(Long id);

  Optional<User> findByVerifiedToken(String token); 
}
