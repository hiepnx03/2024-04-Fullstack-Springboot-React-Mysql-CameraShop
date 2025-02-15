package com.example.demo.config;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.IntStream;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(CategoryRepository categoryRepository, ProductRepository productRepository,
                      RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createAdminRoleIfNotExist();
        createClientRoleIfNotExist();

        createAdminUserIfNotExist();
        createClientUserIfNotExist();

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
        System.out.println("30 products added successfully!");
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

    // Tạo role ADMIN nếu chưa tồn tại
    private void createAdminRoleIfNotExist() {
        Optional<Role> adminRole = roleRepository.findByName("ADMIN");
        if (adminRole.isEmpty()) {
            Role role = new Role();
            role.setName("ADMIN");
            roleRepository.save(role);
            System.out.println("Admin role created!");
        }
    }

    private void createClientRoleIfNotExist() {
        Optional<Role> adminRole = roleRepository.findByName("CLIENT");
        if (adminRole.isEmpty()) {
            Role role = new Role();
            role.setName("CLIENT");
            roleRepository.save(role);
            System.out.println("Client role created!");
        }
    }

    // Tạo user ADMIN nếu chưa tồn tại và gán role ADMIN
    private void createAdminUserIfNotExist() {
        Optional<User> adminUser = Optional.ofNullable(userRepository.findByUserName("ADMIN"));
        if (adminUser.isEmpty()) {
            // Tạo user ADMIN
            User user = new User();
            user.setUserName("ADMIN");
            user.setPassword(passwordEncoder.encode("ADMIN")); // Mã hóa password
            user.setStatus(1); // Đặt trạng thái là true (active)
            user.setEnabled(true);
            user.setPhone("0123456789");
            user.setCreatedBy("ADMIN");
            user.setUpdatedBy("ADMIN");
            user.setEmail("admin@gmail.com");
            user.setFirstName("Admin");
            user.setLastName("Nguyen Van");
            // Tìm role ADMIN và gán cho user ADMIN
            Optional<Role> adminRole = roleRepository.findByName("ADMIN");
            if (adminRole.isPresent()) {
                user.setRoles(Collections.singletonList(adminRole.get()));
            } else {
                // Nếu role ADMIN không tồn tại, tạo mới và gán cho user
                Role role = new Role();
                role.setName("ADMIN");
                roleRepository.save(role);
                user.setRoles(Collections.singletonList(role));
            }

            userRepository.save(user); // Lưu user ADMIN vào cơ sở dữ liệu
            System.out.println("Admin user created successfully!");
        }
    }

    // Tạo user ADMIN nếu chưa tồn tại và gán role ADMIN
    private void createClientUserIfNotExist() {
        Optional<User> clientUser = Optional.ofNullable(userRepository.findByUserName("CLIENT"));
        if (clientUser.isEmpty()) {
            User user = new User();
            user.setUserName("CLIENT");
            user.setPassword(passwordEncoder.encode("CLIENT")); // Mã hóa password
            user.setStatus(1); // Đặt trạng thái là true (active)
            user.setEnabled(true);
            user.setPhone("0123456789");
            user.setCreatedBy("ADMIN");
            user.setUpdatedBy("ADMIN");
            user.setEmail("client@gmail.com");
            user.setFirstName("Client");
            user.setLastName("Nguyen Van");
            Optional<Role> clientRole = roleRepository.findByName("CLIENT");
            if (clientRole.isPresent()) {
                user.setRoles(Collections.singletonList(clientRole.get()));
            } else {
                // Nếu role ADMIN không tồn tại, tạo mới và gán cho user
                Role role = new Role();
                role.setName("CLIENT");
                roleRepository.save(role);
                user.setRoles(Collections.singletonList(role));
            }

            userRepository.save(user); // Lưu user ADMIN vào cơ sở dữ liệu
            System.out.println("Client user created successfully!");
        }
    }

}
