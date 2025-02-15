package com.example.camerashop_be.converter;


import com.example.camerashop_be.dto.ImageDTO;
import com.example.camerashop_be.dto.request.ProductRequest;
import com.example.camerashop_be.dto.response.ProductResponse;
import com.example.camerashop_be.entity.Product;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class ProductConverter {
	private final ModelMapper modelMapper;

	public Product convertToEntity(ProductResponse productResponse) {
		return modelMapper.map(productResponse, Product.class);
	}

	public Product convertToEntity(ProductRequest productRequest) {
		return modelMapper.map(productRequest, Product.class);
	}
	public ProductResponse convertToResponse(Product product) {
		ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
		if(product.getImages()!=null&&product.getImages().size()>0){
			ImageDTO imageDTO = modelMapper.map(product.getImages().get(0), ImageDTO.class);
			productResponse.setImage(imageDTO);
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
