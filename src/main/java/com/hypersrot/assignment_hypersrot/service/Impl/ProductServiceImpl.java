package com.hypersrot.assignment_hypersrot.service.Impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hypersrot.assignment_hypersrot.model.Product;
import com.hypersrot.assignment_hypersrot.repository.ProductRespository;
import com.hypersrot.assignment_hypersrot.service.ProductService;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRespository productRespository;
    @Override
    public Product getInventory() {
        return productRespository.findById(1L).orElse(null);
    }

    @Override
    public void saveProduct(Product product) {
        return;
    }

    @Override
    @Transactional
    public Product updateProduct(Long productId, int Quantity, int availableQuantity, int ordered) {
        Optional<Product> productOptional = productRespository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        Product product = productOptional.get();
        product.setAvailableQuantity(availableQuantity);
        product.setOrdered(ordered);
        product.setQuantity(Quantity);

        return productRespository.save(product);
    }
}
