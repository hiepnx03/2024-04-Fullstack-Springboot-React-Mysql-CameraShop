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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // Chuyển DTO thành Entity
        Product product = productConverter.toEntity(productDTO);

        // Thiết lập danh mục
        if (productDTO.getCategoryIds() != null) {
            Set<Category> categories = productDTO.getCategoryIds().stream()
                    .map(categoryId -> categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy danh mục với ID: " + categoryId)))
                    .collect(Collectors.toSet());
            product.setCategories(categories);
        }

        // Lưu sản phẩm trước để tạo ID (product bây giờ là "hiệu quả final")
        product = productRepository.save(product);

        // Xử lý hình ảnh
        if (productDTO.getImages() != null && !productDTO.getImages().isEmpty()) {
            final Product savedProduct = product; // Tham chiếu final

            // Gán thứ tự ảnh theo vị trí trong danh sách
            Set<Image> images = IntStream.range(0, productDTO.getImages().size())
                    .mapToObj(index -> {
                        ImageDTO imageDTO = (ImageDTO) productDTO.getImages().toArray()[index]; // Lấy từng ảnh
                        Image image = ImageConverter.toEntity(imageDTO);
                        image.setProduct(savedProduct); // Liên kết với sản phẩm
                        image.setImageOrder((long) index + 1); // Gán thứ tự (bắt đầu từ 1)
                        return image;
                    })
                    .collect(Collectors.toSet());

            product.setImages(images);

            imageRepository.saveAll(images); // Lưu ảnh vào database
        }

        // Trả về DTO
        return productConverter.toDTO(product);
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
