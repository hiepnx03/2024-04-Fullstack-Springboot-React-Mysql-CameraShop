package com.example.laptop_be.service.product;

import com.example.laptop_be.dao.ProductRepository;
import com.example.laptop_be.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product addProduct(Product product) {
        // You can add validation or additional logic here if needed
        return productRepository.save(product);
    }

    // Implement other CRUD methods as needed
}
