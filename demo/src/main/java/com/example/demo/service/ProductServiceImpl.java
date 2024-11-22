package com.example.demo.service;

import com.example.demo.converter.ImageConverter;
import com.example.demo.converter.ProductConverter;
import com.example.demo.dto.ImageDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Image;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final ProductConverter productConverter;
    private final CategoryRepository categoryRepository;


    @Override
    @Transactional
    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = productConverter.toEntity(productDTO);

        // Thiết lập danh mục
        if (productDTO.getCategoryIds() != null) {
            Set<Category> categories = productDTO.getCategoryIds().stream()
                    .map(categoryId -> categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy danh mục với ID: " + categoryId)))
                    .collect(Collectors.toSet());
            product.setCategories(categories);
        }

        // Lưu sản phẩm
        product = productRepository.save(product);

        // Tạo thư mục lưu ảnh
        String imageStoragePath = "uploads/images";
        Path storageDirectory = Paths.get(imageStoragePath);
        try {
            Files.createDirectories(storageDirectory);
        } catch (IOException e) {
            throw new RuntimeException("Không thể tạo thư mục lưu trữ ảnh: " + e.getMessage(), e);
        }

        // Xử lý hình ảnh
        if (productDTO.getImages() != null && !productDTO.getImages().isEmpty()) {
            final Product savedProduct = product;

            // Sử dụng mảng nguyên để duy trì index
            final long[] counter = {1};

            Set<Image> images = productDTO.getImages().stream()
                    .filter(imageDTO -> isValidURL(imageDTO.getUrl())) // Bỏ qua URL không hợp lệ
                    .map(imageDTO -> {
                        Image image = ImageConverter.toEntity(imageDTO);

                        // Lưu ảnh vào thư mục
                        String fileName = saveImageToDirectoryWithUniqueName(imageDTO.getUrl(), storageDirectory);

                        image.setProduct(savedProduct);
                        image.setImageOrder(counter[0]++); // Gán thứ tự từ biến counter
                        image.setUrl(fileName); // Lưu đường dẫn file
                        return image;
                    })
                    .collect(Collectors.toSet());

            product.setImages(images);

            imageRepository.saveAll(images); // Lưu ảnh vào database
        }

        return productConverter.toDTO(product);
    }

    // Phương thức kiểm tra tính hợp lệ của URL
    private boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Phương thức tải ảnh từ URL và lưu vào thư mục
    private String saveImageToDirectoryWithUniqueName(String imageUrl, Path storageDirectory) {
        try (InputStream in = new URL(imageUrl).openStream()) {
            // Tạo tên file từ URL
            String originalFileName = FilenameUtils.getName(new URL(imageUrl).getPath());
            Path filePath = storageDirectory.resolve(originalFileName);

            // Nếu file tồn tại, tạo tên file mới với hậu tố
            int counter = 1;
            while (Files.exists(filePath)) {
                String newFileName = FilenameUtils.getBaseName(originalFileName) + "-" + counter + "." + FilenameUtils.getExtension(originalFileName);
                filePath = storageDirectory.resolve(newFileName);
                counter++;
            }

            // Lưu file
            Files.copy(in, filePath);
            System.out.println("Saved image to: " + filePath);
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Không thể lưu ảnh từ URL: " + imageUrl, e);
        }
    }



    // Lấy sản phẩm theo ID
    @Override
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(productConverter::toDTO);
    }

    // Lấy tất cả sản phẩm
    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productConverter::toDTO)
                .collect(Collectors.toList());
    }

    // Xóa sản phẩm theo ID
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}
