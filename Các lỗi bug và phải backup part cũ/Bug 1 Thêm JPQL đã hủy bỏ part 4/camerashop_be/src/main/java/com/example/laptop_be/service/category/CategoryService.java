package com.example.laptop_be.service.category;

import com.example.laptop_be.entity.Category;

import java.util.List;

public interface CategoryService {
//    List<Category> getAllCategories();
//    Category getCategoryById(int id);
    Category createCategory(Category category);
    Category updateCategory(int id, Category category);
//    void deleteCategory (int id);


    //////////////////// JPQL /////////////////////////
    List<Category> getAllCategoriesJPQL();
    Category getCategoryByIdJPQL(int id);
    void deleteCategoryByIdJPQL(int id);
//    void updateCategoryNameByIdJPQL(int id, String categoryName);
//    void createCategoryJPQL(String categoryName);
}
