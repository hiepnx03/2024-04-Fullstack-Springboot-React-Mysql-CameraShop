package com.example.demo.config;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.IntStream;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public DataLoader(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Add Categories");
            System.out.println("2. Add Products");
            System.out.println("3. Add Both");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addCategories();
                    break;
                case 2:
                    List<Category> categories = categoryRepository.findAll();
                    if (categories.isEmpty()) {
                        System.out.println("No categories found. Adding sample categories first.");
                        categories = addCategories();
                    }
                    addProducts(categories);
                    break;
                case 3:
                    List<Category> sampleCategories = addCategories();
                    addProducts(sampleCategories);
                    break;
                case 4:
                    System.out.println("Exiting program...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private List<Category> addCategories() {
        System.out.println("Adding categories...");
        Category electronics = new Category();
        electronics.setName("Electronics");
        categoryRepository.save(electronics);

        Category books = new Category();
        books.setName("Books");
        categoryRepository.save(books);

        Category fashion = new Category();
        fashion.setName("Fashion");
        categoryRepository.save(fashion);

        System.out.println("Categories added successfully!");
        return List.of(electronics, books, fashion);
    }

    private void addProducts(List<Category> categories) {
        System.out.println("Adding products...");
        IntStream.rangeClosed(1, 30).forEach(i -> {
            Product product = new Product();
            product.setName("Product " + i);
            product.setDescription("Description for Product " + i);
            product.setPrice(10.0 * i);
            product.setCategories(getRandomCategories(categories));

            productRepository.save(product);
        });
        System.out.println("20 products added successfully!");
    }

    private Set<Category> getRandomCategories(List<Category> categories) {
        Random random = new Random();
        Set<Category> randomCategories = new HashSet<>();

        // Chọn từ 1 đến 3 danh mục ngẫu nhiên
        int numberOfCategories = random.nextInt(3) + 1;
        for (int i = 0; i < numberOfCategories; i++) {
            Category randomCategory = categories.get(random.nextInt(categories.size()));
            randomCategories.add(randomCategory);
        }

        return randomCategories;
    }
}
