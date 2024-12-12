package com.example.demo.product;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Sử dụng database thật
public class AddNewProductTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testAddNewProduct() {
        // Tạo danh mục mới
        Category category = new Category();
        category.setName("Electronics");
        Category savedCategory = categoryRepository.save(category);

        // Tạo sản phẩm mới và liên kết với danh mục
        Product product = new Product();
        product.setName("Camera");
        product.setDescription("A high-end camera");
        product.setSellPrice(2000.0);
        product.setCategories(Set.of(savedCategory)); // Liên kết danh mục

        // Lưu sản phẩm vào cơ sở dữ liệu
        Product savedProduct = productRepository.save(product);

        // Kiểm tra kết quả
        assertThat(savedProduct).isNotNull(); // Đảm bảo không null
        assertThat(savedProduct.getId()).isNotNull(); // Đảm bảo ID được tự động sinh
        assertThat(savedProduct.getName()).isEqualTo("Laptop"); // Kiểm tra tên sản phẩm
        assertThat(savedProduct.getSellPrice()).isEqualTo(2000.0); // Kiểm tra giá sản phẩm
        assertThat(savedProduct.getCategories()).hasSize(1); // Kiểm tra số lượng danh mục
        assertThat(savedProduct.getCategories())
                .extracting("name")
                .contains("Electronics"); // Kiểm tra tên danh mục
    }
}
