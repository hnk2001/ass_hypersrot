package com.hypersrot.assignment_hypersrot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hypersrot.assignment_hypersrot.model.Order;
import com.hypersrot.assignment_hypersrot.model.Transaction;
import com.hypersrot.assignment_hypersrot.repository.OrderRepository;
import com.hypersrot.assignment_hypersrot.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

     @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Transaction makePayment(Long userId, Long orderId, double amount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);

        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            transaction.setStatus("FAILED");
            transaction.setDescription("Payment Failed due to invalid order id");
            return transactionRepository.save(transaction);
        }
        Order order = orderOptional.get();
        
        if (order.isPaid()) { // Assuming there is a method to check if the order is paid
            transaction.setStatus("FAILED");
            transaction.setDescription("Order is already paid for");
            return transactionRepository.save(transaction);
        }
        
        if (amount <= 0) {
            transaction.setStatus("FAILED");
            transaction.setDescription("Payment Failed as amount is invalid");
            return transactionRepository.save(transaction);
        }
        
        // Simulate payment processing
        // In a real application, you would interact with a payment gateway or service here
        // For now, we'll just mark the order as paid
        order.setPaid(true);
        orderRepository.save(order);

        transaction.setOrder(order);
        transaction.setStatus("SUCCESSFUL");
        transaction.setDescription("Payment successful");

        // Save the transaction to the database
        return transactionRepository.save(transaction);
    }
}
