package com.example.demo.converter;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ImageDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entity.Category;
import com.example.demo.entity.Image;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductConverter {

    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;


    public ProductConverter(ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());

        dto.setStatus(product.getStatus());
        dto.setImportPrice(product.getImportPrice());
        dto.setListPrice(product.getListPrice());
        dto.setSellPrice(product.getSellPrice());
        dto.setQuantity(product.getQuantity());
        dto.setSoldQuantity(product.getSoldQuantity());

        dto.setCategoryIds(product.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));

        dto.setImages(product.getImages().stream()
                .map(ImageConverter::toDTO)
                .collect(Collectors.toSet()));

        return dto;
    }

    public Product toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setImportPrice(dto.getImportPrice());
        product.setListPrice(dto.getListPrice());
        product.setStatus(dto.getStatus());
        product.setSellPrice(dto.getSellPrice());
        product.setQuantity(dto.getQuantity());
        product.setSoldQuantity(dto.getSoldQuantity());

        // Không thiết lập categories và images ở đây, sẽ được thiết lập trong service
        return product;
    }

    public Set<Image> toImageEntities(Set<ImageDTO> imageDTOs, Product product) {
        if (imageDTOs == null || imageDTOs.isEmpty()) {
            return new HashSet<>();
        }

        return imageDTOs.stream().map(imageDTO -> {
            Image image = ImageConverter.toEntity(imageDTO);
            image.setProduct(product); // Thiết lập liên kết với sản phẩm
            return image;
        }).collect(Collectors.toSet());
    }

    public ProductResponse convertToResponse(Product product) {
        ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
//        if(product.getImages()!=null&&product.getImages().size()>0){
//            ImageDTO imageDTO = modelMapper.map(product.getImages().getClass(), ImageDTO.class);
//            productResponse.setImage(imageDTO);
//        }

        if (product.getImages() != null && !product.getImages().isEmpty()) {
            // Lấy hình ảnh đầu tiên (hoặc có thể tùy chỉnh logic này)
            Image firstImage = product.getImages().iterator().next();
            ImageDTO imageDTO = modelMapper.map(firstImage, ImageDTO.class);
            productResponse.setImage(imageDTO);
        } else {
            // Trả về hình ảnh mặc định hoặc null nếu không có hình ảnh
            productResponse.setImage(new ImageDTO(null,"default_images.jpg", 1L));
        }



        if (product.getCategories() != null && !product.getCategories().isEmpty()) {
            // Lấy hình ảnh đầu tiên (hoặc theo logic khác nếu cần)
            Category category = product.getCategories().iterator().next();
            CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
            productResponse.setCategory(categoryDTO);
        }



        return productResponse;
    }

    public Page<ProductResponse> convertToResponse(Page<Product> pageEntity) {
        if (pageEntity == null) {
            return null;
        }
        return pageEntity.map(this::convertToResponse);
    }

}
