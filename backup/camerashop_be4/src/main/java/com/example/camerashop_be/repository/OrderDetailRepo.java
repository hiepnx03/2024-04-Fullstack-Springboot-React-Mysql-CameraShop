package com.example.camerashop_be.repository;

import com.example.camerashop_be.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Long> {
	@Query("select o from OrderDetail o where o.bill.id=:billId and o.bill.user.id=:userId")
	List<OrderDetail> findAllByBillIdAndUserId(@Param("billId") Long id,@Param("userId") Long userId);
	List<OrderDetail> findAllByBillId( Long billId);
}
