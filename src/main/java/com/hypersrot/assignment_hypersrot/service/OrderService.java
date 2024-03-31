package com.hypersrot.assignment_hypersrot.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hypersrot.assignment_hypersrot.model.Coupon;
import com.hypersrot.assignment_hypersrot.model.Order;
import com.hypersrot.assignment_hypersrot.model.Product;
import com.hypersrot.assignment_hypersrot.model.Transaction;
import com.hypersrot.assignment_hypersrot.model.User;
import com.hypersrot.assignment_hypersrot.repository.OrderRepository;
import com.hypersrot.assignment_hypersrot.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentService paymentService;

    @Transactional
    public Order placeOrder(Long userId, int quantity, String couponCode) {
        // Fetch product details
        Product product = productService.getInventory();
        

        // Fetch coupon details
        List<Coupon> coupons = couponService.getAllCoupons();
        Map<String, Integer> couponMap = new HashMap<>();

        for (Coupon coupon : coupons) {
            couponMap.put(coupon.getCode(), coupon.getDiscount());
        }

        if (!couponMap.containsKey(couponCode)) {
            throw new IllegalArgumentException("Invalid coupon");
        }

        // Retrieve the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        //Check if the user has already applied the coupon
        

        // Calculate amount after applying coupon
        double price = product.getPrice();
        int discountPercentage = couponMap.get(couponCode);
        double discountedAmount = price * quantity * (100 - discountPercentage) / 100;

        Date date = new Date();

        // Create order object
        Order order = new Order(user, product, quantity, couponCode, discountedAmount, date);

        // Save the order
        return orderRepository.save(order);
    }

    @Transactional
    public Transaction makePayment(Long userId, Long orderId, double amount) {
        // Fetch the order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // Process payment and save transaction
        Transaction transaction = paymentService.makePayment(userId, orderId, amount);

        System.out.println(transaction.getStatus());
        // If payment is successful, update the order and product
        if (transaction.getStatus() == "SUCCESSFUL") {
            // Mark the order as paid
            order.setPaid(true);
            orderRepository.save(order);

            // Update product quantity
            Product product = order.getProduct();
            System.out.println(product);
            product.setAvailableQuantity(product.getAvailableQuantity() - order.getQuantity());
            product.setOrdered(order.getQuantity() - product.getAvailableQuantity());

            productService.updateProduct(product.getId(), product.getQuantity(), product.getAvailableQuantity(),
                    product.getOrdered());
            productService.saveProduct(product);
        }

        return transaction;
    }
}