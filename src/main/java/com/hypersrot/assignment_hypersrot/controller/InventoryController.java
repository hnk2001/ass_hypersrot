package com.hypersrot.assignment_hypersrot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hypersrot.assignment_hypersrot.model.Product;
import com.hypersrot.assignment_hypersrot.service.ProductService;

@RestController
public class InventoryController {

    @Autowired
    private ProductService productService;

    // Endpoint to get inventory details
    @GetMapping("/inventory")
    public Product getInventory() {
        return productService.getInventory();
    }
}
