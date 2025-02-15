package com.example.camerashop_be.repository;

import com.example.camerashop_be.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepo extends JpaRepository<Role, Integer> {
	Role findByName(String name);
	@Query("select r from Role r join r.users u where u.id=?1")
	List<Role> findAllByUserId(Long userId);


}
