package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {

    private Long id;

    @NotBlank(message = "Tên hãng không được để trống.")
    @Size(min = 3, max = 255, message = "Tên hãng phải có ít nhất 3 ký tự và tối đa 255 ký tự.")
    private String name;

    @Size(max = 1000, message = "Mô tả hãng không được dài quá 1000 ký tự.")
    private String description;

    @NotBlank(message = "Logo không được để trống.")
    private String logo;  // Logo của hãng (URL hoặc tên tệp logo)

    @NotBlank(message = "Website không được để trống.")
    private String website; // Website chính thức của hãng

    @NotNull(message = "Trạng thái hoạt động không được để trống.")
    private boolean active = true; // Trạng thái hoạt động của hãng

    @NotNull(message = "Trạng thái xóa không được để trống.")
    private boolean deleted = false; // Trạng thái xóa (logical delete)

    private List<ProductDTO> products;
}
