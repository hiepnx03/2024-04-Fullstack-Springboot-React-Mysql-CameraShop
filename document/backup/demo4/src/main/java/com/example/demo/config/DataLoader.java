package com.example.demo.config;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.IntStream;

@Component
@Transactional
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VoucherRepository voucherRepository;
    private final ShippingStatusRepository shippingStatusRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final AddressRepository addressRepository;
    private final ImageRepository imageRepository;


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
            System.out.println("3. Add Both Categories and Products");
            System.out.println("4. Create Vouchers");
            System.out.println("5. Create Shipping Statuses");
            System.out.println("6. Create Payments");
            System.out.println("7. Create Orders and Order Details");
            System.out.println("8. Create Addresses");
            System.out.println("9. Add All Data (Categories, Products, Vouchers, Shipping Statuses, Payments, Orders, Addresses)");
            System.out.println("10. Exit");

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
                    createVouchersIfNotExist();
                    break;
                case 5:
                    createShippingStatuses();
                    break;
                case 6:
                    createPayments();
                    break;
                case 7:
                    createOrdersAndOrderDetails();
                    break;
                case 8:
                    createAddresses();
                    break;
                case 9:
                    addAllData();
                    exit = true;
                    break;
                case 10:
                    System.out.println("Exiting program...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    // Phương thức để thêm tất cả dữ liệu
    private void addAllData() {
        // Thêm các danh mục và sản phẩm
        List<Category> categories = addCategories();
        addProducts(categories);

        // Thêm Voucher
        createVouchersIfNotExist();

        // Thêm Shipping Statuses
        createShippingStatuses();

        // Thêm Payments
        createPayments();

        // Thêm Orders và Order Details
        createOrdersAndOrderDetails();

        // Thêm Addresses
        createAddresses();
        createImages();


        System.out.println("All data added successfully!");
    }



    private List<Category> addCategories() {
        System.out.println("Adding categories...");

        // Các danh mục khác
        Category electronics = new Category();
        electronics.setName("Electronics");
        categoryRepository.save(electronics);

        // Thêm danh mục Máy ảnh
        Category camera = new Category();
        camera.setName("Camera");
        categoryRepository.save(camera);

        System.out.println("Categories added successfully!");
        return List.of(electronics, camera); // Trả về danh sách danh mục, bao gồm Camera
    }


    private void addProducts(List<Category> categories) {
        System.out.println("Adding products...");
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Product product = new Product();
            product.setName("Camera " + i); // Đây là tên sản phẩm
            product.setDescription("Mô tả về Máy ảnh " + i);
            product.setImportPrice(2000.0 * i);
            product.setListPrice(2500.0 * i);
            product.setSellPrice(3000.0 * i);
            product.setQuantity(50.0 * i);
            product.setSoldQuantity(10.0 * i);
            product.setCategories(getCameraCategories(categories));

            productRepository.save(product);  // Lưu sản phẩm vào cơ sở dữ liệu
        });

        System.out.println("5 sản phẩm máy ảnh đã được thêm vào cơ sở dữ liệu!");
    }

    private Set<Category> getCameraCategories(List<Category> categories) {
        // Lọc danh mục "Camera" từ danh sách các danh mục đã tạo
        Set<Category> cameraCategories = new HashSet<>();
        for (Category category : categories) {
            if (category.getName().equalsIgnoreCase("Camera")) {
                cameraCategories.add(category);
            }
        }
        return cameraCategories;
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

    private void createVouchersIfNotExist() {
        Voucher voucher1 = new Voucher();
        voucher1.setCode("VOUCHER50");
        voucher1.setCost(50000L);
        voucher1.setStartDate(new Date());
        voucher1.setEndDate(new Date(System.currentTimeMillis() + 100000000L));
        voucher1.setTimes(100);
        voucher1.setStatus(1); // Active
        voucherRepository.save(voucher1);

        Voucher voucher2 = new Voucher();
        voucher2.setCode("VOUCHER100");
        voucher2.setCost(100000L);
        voucher2.setStartDate(new Date());
        voucher2.setEndDate(new Date(System.currentTimeMillis() + 200000000L));
        voucher2.setTimes(50);
        voucher2.setStatus(1); // Active
        voucherRepository.save(voucher2);
    }

    private void createShippingStatuses() {
        ShippingStatus status1 = new ShippingStatus();
        status1.setName("DELIVERING");
        shippingStatusRepository.save(status1);

        ShippingStatus status2 = new ShippingStatus();
        status2.setName("DELIVERED");
        shippingStatusRepository.save(status2);

        ShippingStatus status3 = new ShippingStatus();
        status3.setName("CANCELLED");
        shippingStatusRepository.save(status3);
    }

    private void createPayments() {
        Payment payment1 = new Payment();
        payment1.setPayer("Client");
        payment1.setPaymentMethod(EPaymentMethod.COD);
        payment1.setStatus(EStatusPayment.PAID);
        paymentRepository.save(payment1);

        Payment payment2 = new Payment();
        payment2.setPayer("Admin");
        payment2.setPaymentMethod(EPaymentMethod.PAYPAL);
        payment2.setStatus(EStatusPayment.UNPAID);
        paymentRepository.save(payment2);
    }

    private void createOrdersAndOrderDetails() {
        User client = userRepository.findByUserName("CLIENT");

        ShippingStatus deliveringStatus = shippingStatusRepository.findByName("DELIVERING").orElseThrow();
        Payment payment = paymentRepository.findByPayer("Client").orElseThrow();

        Order order = new Order();
        order.setShippingCost(10000L);
        order.setTotal(100000L);
        order.setDescription("Order for Camera");
        order.setAddress("123 Main St");
        order.setPhone("0987654321");
        order.setFullName("Client Nguyen");
        order.setShippingStatus(deliveringStatus);
        order.setUser(client);
        order.setPayment(payment);
        orderRepository.save(order);

        // OrderDetail for the camera product
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setQuantity(1);
        orderDetail.setPrice(2500L);

        // Lấy sản phẩm từ database
        Product product = productRepository.findByName("Camera 1");
        if (product == null) {
            throw new RuntimeException("Product not found!");
        }
        orderDetail.setProduct(product); // Đảm bảo product không null

        orderDetail.setBill(order); // Đảm bảo rằng order không null
        orderDetailRepository.save(orderDetail);

    }

    private void createAddresses() {
        User client = userRepository.findByUserName("CLIENT");

        Address address = new Address();
        address.setFirstName("Client");
        address.setLastName("Nguyen");
        address.setPhone("0987654321");
        address.setCity("Hanoi");
        address.setDistrict("Hoan Kiem");
        address.setWard("Truc Bach");
        address.setStreet("123 Main St");
        address.setDescription("Home Address");
        address.setIsDefault(1);
        address.setAddressType(EAddressType.HOME);
        address.setUser(client);
        addressRepository.save(address);
    }


    private void createImages() {
        // Lấy sản phẩm "Camera 1" từ database
        Product camera = productRepository.findByName("Camera 1");
        if (camera == null) {
            throw new RuntimeException("Product not found!");
        }

        // Thêm hình ảnh cho sản phẩm Camera 1
        Image image1 = new Image();
        image1.setUrl("https://cdn.vjshop.vn/may-anh/mirrorless/sony/sony-alpha-1-ii/sony-alpha-1-ii-500x500.jpg");
        image1.setImageOrder(1L); // Thứ tự hình ảnh
        image1.setProduct(camera); // Gán sản phẩm cho hình ảnh
        imageRepository.save(image1); // Lưu hình ảnh vào cơ sở dữ liệu

        Image image2 = new Image();
        image2.setUrl("https://cdn.vjshop.vn/may-anh/mirrorless/sony/sony-alpha-7c-ii/sony-alpha-7c-ii-21-1-500x500.jpg");
        image2.setImageOrder(2L); // Thứ tự hình ảnh
        image2.setProduct(camera); // Gán sản phẩm cho hình ảnh
        imageRepository.save(image2); // Lưu hình ảnh vào cơ sở dữ liệu

        System.out.println("Images for product 'Camera 1' added successfully!");
    }



}
