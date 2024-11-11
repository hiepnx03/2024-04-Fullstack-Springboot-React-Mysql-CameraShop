package com.example.camerashop_be.repository;

import com.example.camerashop_be.dto.BestSellingProduct;
import com.example.camerashop_be.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepo extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    //	   @Query("SELECT p FROM Product p WHERE p.category.id = ?1 and p.price<=?2")
    Page<Product> findAllByCategoryIdAndPriceLessThanEqualAndStatus(Long id, Long price, Integer status, Pageable pageable);

    Page<Product> findAllByCategorySlugAndPriceLessThanEqualAndStatus(String slug, Long price, Integer status, Pageable pageable);

    //	   @Query("SELECT p FROM Product p WHERE p.price<=?1")
    Page<Product> findAllByPriceLessThanEqualAndStatus(Long price, Integer status, Pageable pageable);

    Page<Product> findAllByCategoryIdAndPriceLessThanEqual(Long id, Long price, Pageable pageable);

    Page<Product> findAllByCategorySlugAndPriceLessThanEqual(String slug, Long price, Pageable pageable);

    //	   @Query("SELECT p FROM Product p WHERE p.price<=?1")
    Page<Product> findAllByPriceLessThanEqual(Long price, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE (UPPER(p.name) LIKE %?1%  or upper(p.category.name) like %?1%) and p.status=1")
    Page<Product> findAllByNameOrCategoryName(String q, Pageable pageable);

    Product findBySlug(String slug);


    @Query("SELECT new com.example.befruit.dto.BestSellingProduct(o.product.id,o.product.name,o.product.price,o.product.status, sum(o.quantity))   FROM Order b join b.orderDetails o join o.product where month(b.createdAt)=?1 group by o.product.id,o.product.name,o.product.price,o.product.status order by sum(o.quantity) desc")
    Page<BestSellingProduct> getBestSellingInMonth(Integer month, Pageable pageable);
}
