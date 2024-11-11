package com.example.laptop_be.converter;

import com.example.laptop_be.dto.*;
import com.example.laptop_be.entity.*;
import com.example.laptop_be.repository.CategoryRepository;
import com.example.laptop_be.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class Converter {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    // CartItem
    public CartItemDTO convertToDto(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemDTO.class);
    }

    public CartItem convertToEntity(CartItemDTO cartItemDTO) {
        return modelMapper.map(cartItemDTO, CartItem.class);
    }

    // Category
    public CategoryDTO convertToDto(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    public Category convertToEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }

    // Delivery
    public DeliveryDTO convertToDto(Delivery delivery) {
        return modelMapper.map(delivery, DeliveryDTO.class);
    }

    public Delivery convertToEntity(DeliveryDTO deliveryDTO) {
        return modelMapper.map(deliveryDTO, Delivery.class);
    }

    // FavoriteProduct
    public FavoriteProductDTO convertToDto(FavoriteProduct favoriteProduct) {
        return modelMapper.map(favoriteProduct, FavoriteProductDTO.class);
    }

    public FavoriteProduct convertToEntity(FavoriteProductDTO favoriteProductDTO) {
        return modelMapper.map(favoriteProductDTO, FavoriteProduct.class);
    }

    // Feedbacks
    public FeedbacksDTO convertToDto(Feedbacks feedbacks) {
        return modelMapper.map(feedbacks, FeedbacksDTO.class);
    }

    public Feedbacks convertToEntity(FeedbacksDTO feedbacksDTO) {
        return modelMapper.map(feedbacksDTO, Feedbacks.class);
    }

    // Image
    public ImageDTO convertToDto(Image image) {
        return modelMapper.map(image, ImageDTO.class);
    }

    public Image convertToEntity(ImageDTO imageDTO) {
        return modelMapper.map(imageDTO, Image.class);
    }

    // Notification
    public NotificationDTO convertToDto(Notification notification) {
        return modelMapper.map(notification, NotificationDTO.class);
    }

    public Notification convertToEntity(NotificationDTO notificationDTO) {
        return modelMapper.map(notificationDTO, Notification.class);
    }

    // Order
    public OrderDTO convertToDto(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }

    public Order convertToEntity(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }

    // OrderDetail
    public OrderDetailDTO convertToDto(OrderDetail orderDetail) {
        return modelMapper.map(orderDetail, OrderDetailDTO.class);
    }

    public OrderDetail convertToEntity(OrderDetailDTO orderDetailDTO) {
        return modelMapper.map(orderDetailDTO, OrderDetail.class);
    }

    // Payment
    public PaymentDTO convertToDto(Payment payment) {
        return modelMapper.map(payment, PaymentDTO.class);
    }

    public Payment convertToEntity(PaymentDTO paymentDTO) {
        return modelMapper.map(paymentDTO, Payment.class);
    }

    // Product
    // Product
    public Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setIdProduct(productDTO.getIdProduct());
        product.setProductName(productDTO.getProductName());
        product.setListPrice(productDTO.getListPrice());
        product.setSellPrice(productDTO.getSellPrice());
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());
        product.setAvgRating(productDTO.getAvgRating());
        product.setSoldQuantity(productDTO.getSoldQuantity());
        product.setDiscountPercent(productDTO.getDiscountPercent());

        if (productDTO.getCategoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(productDTO.getCategoryIds());
            product.setCategoryList(categories);
        }

        if (productDTO.getImageIds() != null) {
            List<Image> images = imageRepository.findAllById(productDTO.getImageIds());
            for (Image image : images) {
                image.setProduct(product);
            }
            product.setImageList(images);
        }

        return product;
    }

    public ProductDTO convertToDto(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setIdProduct(product.getIdProduct());
        productDTO.setProductName(product.getProductName());
        productDTO.setListPrice(product.getListPrice());
        productDTO.setSellPrice(product.getSellPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setAvgRating(product.getAvgRating());
        productDTO.setSoldQuantity(product.getSoldQuantity());
        productDTO.setDiscountPercent(product.getDiscountPercent());

        if (product.getCategoryList() != null) {
            productDTO.setCategoryIds(product.getCategoryList().stream()
                    .map(Category::getIdCategory)
                    .collect(Collectors.toList()));
        }

        if (product.getImageList() != null) {
            productDTO.setImageIds(product.getImageList().stream()
                    .map(Image::getIdImage)
                    .collect(Collectors.toList()));
        }

        return productDTO;
    }

    // Review
    public ReviewDTO convertToDto(Review review) {
        return modelMapper.map(review, ReviewDTO.class);
    }

    public Review convertToEntity(ReviewDTO reviewDTO) {
        return modelMapper.map(reviewDTO, Review.class);
    }

    // Role
    public RoleDTO convertToDto(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

    public Role convertToEntity(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, Role.class);
    }

    // User
    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
