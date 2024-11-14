package com.example.camerashop_be.repository;

import com.example.camerashop_be.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {
	List<Image> findAllByProductId(Long productId);
	@Query("select count(i) from Image i where i.product.id=?1")
	Integer getSizeByProductId(Long productId);
	Image findTopByProductId(Long productId);
}
