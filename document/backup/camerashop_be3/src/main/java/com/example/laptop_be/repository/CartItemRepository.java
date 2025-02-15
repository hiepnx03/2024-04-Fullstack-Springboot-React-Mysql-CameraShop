package com.example.laptop_be.repository;

import com.example.laptop_be.entity.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "cart-items")
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem c WHERE  c.user.idUser = :idUser")
    public void deleteCartItemsByIdUser(@Param("idUser") int idUser);
}
