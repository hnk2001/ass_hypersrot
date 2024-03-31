package com.hypersrot.assignment_hypersrot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hypersrot.assignment_hypersrot.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    public Optional<User> findById(Long id);
}
