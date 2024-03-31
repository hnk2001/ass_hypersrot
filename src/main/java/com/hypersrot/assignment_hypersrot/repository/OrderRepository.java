package com.hypersrot.assignment_hypersrot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hypersrot.assignment_hypersrot.model.Order;
import com.hypersrot.assignment_hypersrot.model.User;

public interface OrderRepository extends JpaRepository<Order, Long>{

    List<Order> findByUserId(Long userId);

    Object findByUserIdAndId(Long userId, Long orderId);

    @Query("SELECT o FROM Order o WHERE o.user = :user AND o.couponApplied = :couponCode")
    List<Order> findByUserAndCouponApplied(@Param("user") User user, @Param("couponCode") String couponCode);
    
}
