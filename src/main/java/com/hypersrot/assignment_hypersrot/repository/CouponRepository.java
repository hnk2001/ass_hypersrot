package com.hypersrot.assignment_hypersrot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hypersrot.assignment_hypersrot.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long>{
    
}
