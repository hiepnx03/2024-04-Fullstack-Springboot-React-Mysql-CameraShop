package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long id;

    @NotBlank(message = "Category name cannot be empty.")
    @Size(min = 3, max = 255, message = "Category name must be between 3 and 255 characters.")
    private String name;  // Tên danh mục không được để trống, phải có độ dài từ 3 đến 255 ký tự.

    @Size(max = 1000, message = "Description cannot exceed 1000 characters.")
    private String description;  // Mô tả không được dài quá 1000 ký tự.

    private String image;  // Hình ảnh có thể để trống nếu không cần thiết.

    private boolean active;  // Trạng thái hoạt động không cần validation vì là kiểu boolean

    private boolean deleted;  // Trạng thái xóa không cần validation vì là kiểu boolean

    private boolean editable;  // Trạng thái có thể chỉnh sửa không cần validation vì là kiểu boolean

    private boolean visible;  // Trạng thái hiển thị không cần validation vì là kiểu boolean
    private Integer status;
    private String slug;
    @NotNull(message = "Product IDs cannot be null.")
    private Set<Long> productIds = new HashSet<>(); // Danh sách các sản phẩm không được null

    @NotNull(message = "Brand ID cannot be null.")
    private Long brandId;  // ID của thương hiệu không được null
}
