package com.hypersrot.assignment_hypersrot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hypersrot.assignment_hypersrot.model.Order;
import com.hypersrot.assignment_hypersrot.repository.OrderRepository;

@Service
public class OrderStatusService {
    @Autowired
    private OrderRepository orderRepository;

    // Method to get orders of a user
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // Method to get details of a specific order
    public Order getOrder(Long userId, Long orderId) {
        return (Order) orderRepository.findByUserIdAndId(userId, orderId);

    }
}
