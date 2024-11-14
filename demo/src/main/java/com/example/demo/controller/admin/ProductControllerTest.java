//package com.example.demo.controller.admin;
//
//import com.example.demo.dto.ProductDTO;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.AllArgsConstructor;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.List;
//import java.util.Set;
//
//import static org.springframework.http.RequestEntity.post;
//
//@SpringBootTest
//@AllArgsConstructor
//public class ProductControllerTest {
//    private final MockMvc mockMvc;
//    private final ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void setUp() {
//        // Thiết lập trước khi mỗi test case được thực thi
//    }
//
//    @Test
//    public void testCreateProduct() throws Exception {
//        // Tạo một ProductDTO để gửi vào yêu cầu POST
//        ProductDTO productDTO = new ProductDTO();
//        productDTO.setName("Sản phẩm A");
//        productDTO.setDescription("Mô tả sản phẩm A");
//        productDTO.setPrice(100000.0);
//        productDTO.setCategory(Set.of("Category1"));
//        productDTO.setImages((Set<String>) List.of("https://example.com/image1.jpg", "https://example.com/image2.jpg"));
//
//        mockMvc.perform((org.springframework.test.web.servlet.RequestBuilder) post("/products")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.valueOf(objectMapper.writeValueAsString(productDTO))))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sản phẩm A"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(100000.0))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.category[0]").value("Category1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.images[0]").value("https://example.com/image1.jpg"));
//    }
//}
