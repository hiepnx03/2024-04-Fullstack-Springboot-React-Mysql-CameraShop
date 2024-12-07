package com.example.demo.category;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AddMultipleCategoriesTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void AddMultipleCategoriesTest1() {
    // Tạo danh mục 1
        Category category1 = new Category();
        category1.setName("Electronics");

        // Tạo danh mục 2
        Category category2 = new Category();
        category2.setName("Books");

        // Lưu vào database
        categoryRepository.saveAll(Set.of(category1, category2));

        // Lấy danh mục từ database
        Iterable<Category> categories = categoryRepository.findAll();

        // Kiểm tra danh sách danh mục
        assertThat(categories).hasSize(2);
        assertThat(categories).extracting("name").contains("Electronics", "Books");
    }
}
