package com.example.laptop_be.repository;

import com.example.laptop_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(excerptProjection = User.class, path = "users")
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
    public User findByEmail(String email);
}
