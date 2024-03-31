package com.hypersrot.assignment_hypersrot.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hypersrot.assignment_hypersrot.model.Order;
import com.hypersrot.assignment_hypersrot.model.Product;
import com.hypersrot.assignment_hypersrot.model.Transaction;
import com.hypersrot.assignment_hypersrot.model.User;
import com.hypersrot.assignment_hypersrot.repository.OrderRepository;
import com.hypersrot.assignment_hypersrot.repository.TransactionRepository;
import com.hypersrot.assignment_hypersrot.repository.UserRepository;
import com.hypersrot.assignment_hypersrot.service.OrderService;
import com.hypersrot.assignment_hypersrot.service.OrderStatusService;
import com.hypersrot.assignment_hypersrot.service.PaymentService;
import com.hypersrot.assignment_hypersrot.service.ProductService;

import jakarta.servlet.http.HttpSession;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderStatusService orderStatusService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping("/placeOrder/{userId}")
    public ResponseEntity<?> placeOrder(
            @PathVariable Long userId,
            @RequestParam int qty,
            @RequestParam String coupon,
            HttpSession session) {
        try {
            // Check if userId is valid
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            // Check if the coupon is already applied for the user
            List<Order> userOrders = orderRepository.findByUserAndCouponApplied(user, coupon);
            if (!userOrders.isEmpty()) {
                session.setAttribute("couponAppliedError",
                        "Coupon you selected has already been applied. Try another.");
                return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/").build();
            }

            Product product = productService.getInventory();
            if (product == null || qty <= 0 || qty > product.getAvailableQuantity()) {
                session.setAttribute("qtyError",
                        "Invalid quantity");
                return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/").build();
            }
            // Place order and get the order details
            Order order = orderService.placeOrder(userId, qty, coupon);

            // Set session attributes
            session.setAttribute("orderId", order.getId());
            session.setAttribute("userId", userId);
            session.setAttribute("amount", order.getAmount());

            // Redirect to payment page with order details
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "/payment?userId=" + userId + "&orderId=" + order.getId())
                    .build();
        } catch (IllegalArgumentException e) {
            // Handle validation errors (Invalid quantity or coupon)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Handle other errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{userId}/{orderId}/pay")
    public ResponseEntity<?> processPayment(
            @PathVariable Long userId,
            @PathVariable Long orderId,
            @RequestParam double amount) {
        try {
            // Process payment and save transaction
            Transaction transaction = paymentService.makePayment(userId, orderId, amount);

            // If payment is successful, save the order and update product table
            System.out.println(transaction.getStatus());
            if (transaction.getStatus() == "SUCCESSFUL") {
                // Save the order
                Optional<Order> order = orderRepository.findById(orderId);
                Order order2 = order.get();
                orderRepository.save(order2);

                // Updating Product Table
                Product product = order2.getProduct();
                System.out.println(product);
                product.setAvailableQuantity(product.getAvailableQuantity() - order2.getQuantity());
                product.setOrdered(product.getQuantity() - product.getAvailableQuantity());

                productService.updateProduct(product.getId(), product.getQuantity(), product.getAvailableQuantity(),
                        product.getOrdered());

            }

            // Return transaction details
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to get orders by userId
    @GetMapping("/{userId}/orders")
    public List<Map<String, Object>> getUserOrders(@PathVariable Long userId) {
        List<Order> userOrders = orderStatusService.getUserOrders(userId);
        List<Map<String, Object>> formattedOrders = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        for (Order order : userOrders) {
            Map<String, Object> formattedOrder = new HashMap<>();
            formattedOrder.put("orderId", order.getId());
            formattedOrder.put("amount", order.getAmount());
            formattedOrder.put("date", dateFormat.format(order.getOrderDate())); // Format date as dd-MM-yyyy
            formattedOrder.put("coupon", order.getCouponApplied());
            formattedOrders.add(formattedOrder);
        }

        return formattedOrders;
    }

    // Endpoint to get order details by userId and orderId
    @GetMapping("/{userId}/orders/{orderId}")
    public Map<String, Object> getOrderDetails(@PathVariable Long userId, @PathVariable Long orderId) {
        Order order = orderStatusService.getOrder(userId, orderId);
        Map<String, Object> orderDetails = new HashMap<>();
    
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
        orderDetails.put("orderId", order.getId());
        orderDetails.put("amount", order.getAmount());
        orderDetails.put("date", dateFormat.format(order.getOrderDate())); // Format date as dd-MM-yyyy
        orderDetails.put("coupon", order.getCouponApplied());
    
        // Fetch transaction details based on orderId
        Transaction transaction = transactionRepository.findByOrderId(orderId);
        if (transaction != null) {
            orderDetails.put("transactionId", transaction.getId());
            orderDetails.put("status", transaction.getStatus());
        }
    
        return orderDetails;
    }
}
