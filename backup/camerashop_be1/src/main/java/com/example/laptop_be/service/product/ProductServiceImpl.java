package com.example.laptop_be.service.product;

import com.example.laptop_be.dao.CategoryRepository;
import com.example.laptop_be.dao.ProductRepository;
import com.example.laptop_be.entity.Category;
import com.example.laptop_be.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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



    /////////////////
    @Override
    @Transactional
    public ResponseEntity<?> addProduct1(Product product) {
        try {
            // Save the product
            Product addedProduct = productRepository.save(product);

            // Fetch categories by their IDs and filter out any null values
            List<Category> categoryList = product.getCategoryList().stream()
                    .map(category -> {
                        Category fetchedCategory = categoryRepository.findById(category.getIdCategory()).orElse(null);
                        return fetchedCategory;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // Set the fetched categories to the product and update it
            addedProduct.setCategoryList(categoryList);
            productRepository.save(addedProduct);

            return ResponseEntity.ok("Success!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }






    // Implement other CRUD methods as needed
}
