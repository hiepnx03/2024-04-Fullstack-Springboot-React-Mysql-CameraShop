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
    private final BrandRepository brandRepository;


    @Override
    public void run(String... args) {


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
        createAdminRoleIfNotExist();
        createClientRoleIfNotExist();
        createAdminUserIfNotExist();
        createClientUserIfNotExist();

        addBrands();
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


    private List<Brand> addBrands() {
        System.out.println("Adding camera brands...");

        // Thêm các brand máy ảnh

        // Brand 1: Canon
        Brand brand = new Brand();
        brand.setName("Canon");
        brand.setDescription("Canon is a global leader in imaging and optical products, known for its cameras and lenses.");
        brand.setLogo("https://example.com/canon_logo.png");
        brand.setWebsite("https://www.canon.com");
        brand.setActive(true);
        brand.setCreatedBy("ADMIN");
        brand.setUpdatedBy("ADMIN");
        brand.setDeleted(false);
        brandRepository.save(brand);

        // Brand 2: Sony
        Brand brand1 = new Brand();
        brand1.setName("Sony");
        brand1.setDescription("Sony is a leading manufacturer of digital cameras and lenses.");
        brand1.setLogo("https://example.com/sony_logo.png");
        brand1.setWebsite("https://www.sony.com");
        brand1.setActive(true);
        brand1.setCreatedBy("ADMIN");
        brand1.setUpdatedBy("ADMIN");
        brand1.setDeleted(false);
        brandRepository.save(brand1);

        // Brand 3: Nikon
        Brand brand2 = new Brand();
        brand2.setName("Nikon");
        brand2.setDescription("Nikon is a global leader in the photography industry, specializing in digital cameras and lenses.");
        brand2.setLogo("https://example.com/nikon_logo.png");
        brand2.setWebsite("https://www.nikon.com");
        brand2.setActive(true);
        brand2.setCreatedBy("ADMIN");
        brand2.setUpdatedBy("ADMIN");
        brand2.setDeleted(false);
        brandRepository.save(brand2);

        // Brand 4: Fujifilm
        Brand brand3 = new Brand();
        brand3.setName("Fujifilm");
        brand3.setDescription("Fujifilm is a well-known brand for cameras, photographic film, and imaging equipment.");
        brand3.setLogo("https://example.com/fujifilm_logo.png");
        brand3.setWebsite("https://www.fujifilm.com");
        brand3.setActive(true);
        brand3.setCreatedBy("ADMIN");
        brand3.setUpdatedBy("ADMIN");
        brand3.setDeleted(false);
        brandRepository.save(brand3);

        // Brand 5: Panasonic
        Brand brand4 = new Brand();
        brand4.setName("Panasonic");
        brand4.setDescription("Panasonic manufactures cameras, camcorders, and optical equipment.");
        brand4.setLogo("https://example.com/panasonic_logo.png");
        brand4.setWebsite("https://www.panasonic.com");
        brand4.setActive(true);
        brand4.setCreatedBy("ADMIN");
        brand4.setUpdatedBy("ADMIN");
        brand4.setDeleted(false);
        brandRepository.save(brand4);

        System.out.println("Camera brands added successfully!");

        // Trả về danh sách các hãng máy ảnh đã thêm
        return List.of(brand, brand1, brand2, brand3, brand4);
    }





    private List<Category> addCategories() {
        System.out.println("Adding camera-related categories...");

        // Lấy các Brand đã tạo từ trước
        Brand canonBrand = brandRepository.findByName("Canon");
        Brand sonyBrand = brandRepository.findByName("Sony");
        Brand nikonBrand = brandRepository.findByName("Nikon");
        Brand fujifilmBrand = brandRepository.findByName("Fujifilm");
        Brand panasonicBrand = brandRepository.findByName("Panasonic");

        // Thêm danh mục cho Canon
        Category camera = new Category();
        camera.setName("Camera");
        camera.setSlug("camera");
        camera.setDescription("Digital cameras including DSLR, mirrorless, and compact cameras.");
        camera.setImage("https://example.com/camera_image.png");
        camera.setActive(true);
        camera.setDeleted(false);
        camera.setEditable(true);
        camera.setVisible(true);
        camera.setStatus(1);
        camera.setBrand(canonBrand);  // Gán Brand Canon cho Category Camera
        camera.setCreatedBy("ADMIN");
        camera.setUpdatedBy("ADMIN");
        categoryRepository.save(camera);  // Lưu danh mục

        // Thêm danh mục cho Sony
        Category mirrorless = new Category();
        mirrorless.setName("Mirrorless Cameras");
        mirrorless.setSlug("mirrorless cameras");
        mirrorless.setDescription("Mirrorless cameras for professional and personal use, offering high performance.");
        mirrorless.setImage("https://example.com/mirrorless_image.png");
        mirrorless.setActive(true);
        mirrorless.setDeleted(false);
        mirrorless.setEditable(true);
        mirrorless.setVisible(true);
        mirrorless.setStatus(1);
        mirrorless.setBrand(sonyBrand);  // Gán Brand Sony cho Category Mirrorless
        mirrorless.setCreatedBy("ADMIN");
        mirrorless.setUpdatedBy("ADMIN");
        categoryRepository.save(mirrorless);  // Lưu danh mục

        // Thêm danh mục cho Nikon
        Category dslr = new Category();
        dslr.setName("DSLR Cameras");
        dslr.setSlug("dslr cameras");
        dslr.setDescription("DSLR cameras, offering high-quality photography with advanced features.");
        dslr.setImage("https://example.com/dslr_image.png");
        dslr.setActive(true);
        dslr.setDeleted(false);
        dslr.setEditable(true);
        dslr.setVisible(true);
        dslr.setStatus(1);
        dslr.setBrand(nikonBrand);  // Gán Brand Nikon cho Category DSLR
        dslr.setCreatedBy("ADMIN");
        dslr.setUpdatedBy("ADMIN");
        categoryRepository.save(dslr);  // Lưu danh mục

        // Thêm danh mục cho Fujifilm
        Category instant = new Category();
        instant.setName("Instant Cameras");
        instant.setSlug("instant cameras");
        instant.setDescription("Instant cameras for fun, instant photo printing.");
        instant.setImage("https://example.com/instant_image.png");
        instant.setActive(true);
        instant.setDeleted(false);
        instant.setEditable(true);
        instant.setVisible(true);
        instant.setStatus(1);
        instant.setBrand(fujifilmBrand);  // Gán Brand Fujifilm cho Category Instant Cameras
        instant.setCreatedBy("ADMIN");
        instant.setUpdatedBy("ADMIN");
        categoryRepository.save(instant);  // Lưu danh mục

        // Thêm danh mục cho Panasonic
        Category camcorders = new Category();
        camcorders.setName("Camcorders");
        camcorders.setSlug("camcorders");
        camcorders.setDescription("High-definition camcorders for video recording and filmmaking.");
        camcorders.setImage("https://example.com/camcorders_image.png");
        camcorders.setActive(true);
        camcorders.setDeleted(false);
        camcorders.setEditable(true);
        camcorders.setVisible(true);
        camcorders.setStatus(1);
        camcorders.setBrand(panasonicBrand);  // Gán Brand Panasonic cho Category Camcorders
        camcorders.setCreatedBy("ADMIN");
        camcorders.setUpdatedBy("ADMIN");
        categoryRepository.save(camcorders);  // Lưu danh mục

        System.out.println("Camera-related categories added successfully!");

        // Trả về danh sách các Category đã thêm
        return List.of(camera, mirrorless, dslr, instant, camcorders);
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
            product.setStatus(1);
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

        ShippingStatus deliveringStatus = shippingStatusRepository.findByName("DELIVERING");
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
