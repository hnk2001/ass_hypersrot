package com.hypersrot.assignment_hypersrot.model;

import java.util.Date;
import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    private int quantity;

    private boolean isPaid;
    private String couponApplied;

    private double amount; // Adding amount field
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    public Order(User user, Product product, int quantity, String couponApplied, double amount, Date orderDate) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.couponApplied = couponApplied;
        this.amount = amount;
        this.orderDate = orderDate;
    }

    public Order() {
        
    }
  

   
}