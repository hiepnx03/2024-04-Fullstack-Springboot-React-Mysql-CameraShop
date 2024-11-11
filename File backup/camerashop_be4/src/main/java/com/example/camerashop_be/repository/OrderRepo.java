package com.example.camerashop_be.repository;

import com.example.camerashop_be.dto.ShippingStatusStatistical;
import com.example.camerashop_be.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> , JpaSpecificationExecutor<Order> {

//	@Query("select b from Bill b where b.address.user.id=:userId and b.status=:status")
	Page<Order> findAllByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Integer status, Pageable pageable);
	Page<Order> findAllByStatus(Integer status, Pageable pageable);
	@Query("select count(b) from Order b")
	Integer totalOrders();
	@Query("select count(b) from Order b where cast(b.createdAt as date) = cast(?1 as date)")
	Integer totalOrdersInDay(Date date);

	@Query("select sum(b.total) from Order b ")
	Integer totalRevenue();
	@Query("select sum(b.total) from Order b where month(b.createdAt)=?1")
	Integer totalRevenueMonth(Integer month);
	@Query("SELECT new com.example.befruit.dto.ShippingStatusStatistical(s.name,count(b))  FROM Order b join b.shippingStatus s group by s.name")
	List<ShippingStatusStatistical> statisticalShippingStatus();


}
