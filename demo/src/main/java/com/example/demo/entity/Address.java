package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address extends Base<String> {
	private String firstName;
	private String lastName;
	private String phone;
	private String city;
	private String district;
	private String ward;
	private String street;
	private String description;
	private Integer isDefault;
	private Integer status;

	@Enumerated(EnumType.STRING)
	@Column(name = "address_type")
	private EAddressType addressType;

	private boolean isDefaultAddress;  // địa chỉ mặc định
	private boolean isPickupAddress;   // địa chỉ lấy hàng
	private boolean isReturnAddress;   // địa chỉ trả hàng

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;
}
