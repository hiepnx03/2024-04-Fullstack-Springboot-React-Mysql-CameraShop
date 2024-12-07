package com.example.demo.category;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AddNewCategoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void AddNewCategoryTest1() {
        // Tạo một danh mục mới
        Category category = new Category();
        category.setName("Electronics");

        // Lưu danh mục vào cơ sở dữ liệu
        Category savedCategory = categoryRepository.save(category);

        // Kiểm tra kết quả
        assertThat(savedCategory).isNotNull(); // Đảm bảo không null
        assertThat(savedCategory.getId()).isNotNull(); // Đảm bảo ID được tự động sinh
        assertThat(savedCategory.getName()).isEqualTo("Electronics"); // Kiểm tra tên danh mục
    }

}
