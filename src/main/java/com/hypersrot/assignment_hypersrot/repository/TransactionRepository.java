package com.hypersrot.assignment_hypersrot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hypersrot.assignment_hypersrot.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    
    @Query("SELECT t FROM Transaction t WHERE t.order.id = :orderId")
    Transaction findByOrderId(Long orderId);
}
