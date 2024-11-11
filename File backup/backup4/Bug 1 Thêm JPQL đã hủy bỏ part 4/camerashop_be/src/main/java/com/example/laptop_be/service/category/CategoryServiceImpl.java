package com.example.laptop_be.service.category;

import com.example.laptop_be.dao.CategoryRepository;
import com.example.laptop_be.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


//    @Override
//    public List<Category> getAllCategories() {
//        return categoryRepository.findAll();
//    }
//
//
//    @Override
//    public Category getCategoryById(int id) {
//        return categoryRepository.findById(id).orElse(null);
//    }
//
    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(int id, Category category) {
        if (categoryRepository.existsById(id)){
            category.setIdCategory(id);
            return categoryRepository.save(category);
        }
        return null;
    }
//
//    @Override
//    public void deleteCategory(int id) {
//        categoryRepository.deleteById(id);
//    }


    ///////////////////// JPQL //////////////////////
    @Override
    public List<Category> getAllCategoriesJPQL() {
        return categoryRepository.getAllCategoriesJPQL();
    }

    @Override
    public Category getCategoryByIdJPQL(int id) {
        return categoryRepository.getCategoryByIdJPQL(id);
    }
    @Override
    public void deleteCategoryByIdJPQL(int id) {
        categoryRepository.deleteCategoryByIdJPQL(id);
    }

//    @Override
//    public void updateCategoryNameByIdJPQL(int id, String categoryName) {
//        categoryRepository.updateCategoryNameByIdJPQL(id, categoryName);
//    }
//
//    @Override
//    public void createCategoryJPQL(String categoryName) {
//        try {
//            categoryRepository.createCategoryJPQL(categoryName);
//            System.out.println("Thêm mới danh mục thành công.");
//        }catch (Exception ex){
//            System.err.println("Lỗi khi thêm mới danh mục: " + ex.getMessage());
//        }
//    }
}
