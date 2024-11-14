package com.example.camerashop_be.repository;

import com.example.camerashop_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserRepo extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {
	User findByVerificationCode(String code);

	User findByEmail(String email);

	User findByUserNameAndStatus(String userName, Integer status);

	User findByEmailAndStatus(String userName, Integer status);

	boolean existsByUserName(String username);

	boolean existsByEmail(String email);

	User findByToken(String token);
	@Query("select count(u) from User u join u.roles r where r.name=?1")
	Integer countByRoleName(String name);

	@Query("select count(b) from User b join b.roles r where r.name='client'")
	Integer totalOrders();
	@Query("select count(b) from User b join b.roles r where r.name='client' and cast(b.createdAt as date) = cast(?1 as date)")
	Integer totalOrdersInDay(Date date);


}
