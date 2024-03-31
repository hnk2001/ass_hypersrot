package com.hypersrot.assignment_hypersrot.service;

import com.hypersrot.assignment_hypersrot.model.Product;

public interface ProductService {
    public Product getInventory();

    public void saveProduct(Product product);

    public Product updateProduct(Long productId, int Quantity, int availableQuantity, int ordered);
}
