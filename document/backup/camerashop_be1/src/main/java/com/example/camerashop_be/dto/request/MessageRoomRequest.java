package com.example.camerashop_be.dto.request;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRoomRequest {
	private Long id;
	private String content;
	private String createdBy;
	private Date createdDate;
	private Long chatRoomId;
}
