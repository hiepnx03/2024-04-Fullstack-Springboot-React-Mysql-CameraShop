//package com.example.demo.config;
//
//
//import com.example.demo.entity.Category;
//import com.example.demo.entity.ERole;
//import com.example.demo.entity.Role;
//import com.example.demo.entity.User;
//import com.example.demo.repository.CategoryRepository;
//import com.example.demo.repository.RoleRepository;
//import com.example.demo.repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    private final CategoryRepository categoryRepository;
//    private final RoleRepository roleRepository;
//    private final UserRepository userRepository;
//
//    public DataInitializer(CategoryRepository categoryRepository, RoleRepository roleRepository, UserRepository userRepository) {
//        this.categoryRepository = categoryRepository;
//        this.roleRepository = roleRepository;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Thêm các dữ liệu mẫu vào bảng Category
//        addCategories();
//
//        // Thêm các dữ liệu mẫu vào bảng Role nếu chưa tồn tại
//        addRoles();
//
//        // Thêm các dữ liệu mẫu vào bảng User nếu chưa tồn tại
//        addUsers();
//    }
//
//    private void addCategories() {
//        if (categoryRepository.count() == 0) {  // Kiểm tra xem đã có dữ liệu trong bảng Category chưa
//            Category category1 = new Category();
//            category1.setName("Electronics");
//            category1.setDescription("Electronic devices");
//            categoryRepository.save(category1);
//
//            Category category2 = new Category();
//            category2.setName("Books");
//            category2.setDescription("Various kinds of books");
//            categoryRepository.save(category2);
//
//            System.out.println("Categories added successfully");
//        }
//    }
//
//    private void addRoles() {
//        if (roleRepository.count() == 0) {  // Kiểm tra xem đã có dữ liệu trong bảng Role chưa
//            Role roleAdmin = new Role();
//            roleAdmin.setName(ERole.ADMIN.name());
//            roleRepository.save(roleAdmin);
//
//            Role roleUser = new Role();
//            roleUser.setName(ERole.USER.name());
//            roleRepository.save(roleUser);
//
//            System.out.println("Roles added successfully");
//        }
//    }
//
//    private void addUsers() {
//        if (userRepository.count() == 0) {  // Kiểm tra xem đã có dữ liệu trong bảng User chưa
//            User admin = new User();
//            admin.setUsername("admin");
//            admin.setPassword("adminpassword");  // Bạn cần mã hóa mật khẩu trước khi lưu
//            admin.setRoles(roleRepository.findAll());  // Gán tất cả role cho user admin
//            userRepository.save(admin);
//
//            User user = new User();
//            user.setUsername("user");
//            user.setPassword("userpassword");  // Mã hóa mật khẩu trước khi lưu
//            user.setRoles(roleRepository.findByName(ERole.USER.name()));  // Gán role USER cho user
//
//            userRepository.save(user);
//
//            System.out.println("Users added successfully");
//        }
//    }
//}