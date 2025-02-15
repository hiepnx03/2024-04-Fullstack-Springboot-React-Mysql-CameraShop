package com.example.camerashop_be.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueDTO  {
	private Object createdAt;
	private Integer totalOrder;
	private Long grossRevenue;
	private Long shipping;
	private Long netRevenue;

}
