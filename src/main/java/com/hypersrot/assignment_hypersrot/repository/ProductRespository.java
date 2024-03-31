package com.hypersrot.assignment_hypersrot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hypersrot.assignment_hypersrot.model.Product;

public interface ProductRespository extends JpaRepository<Product, Long>{
    
}
