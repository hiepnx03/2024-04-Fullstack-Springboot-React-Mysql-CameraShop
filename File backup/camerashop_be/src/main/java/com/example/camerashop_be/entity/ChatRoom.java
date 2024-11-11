package com.example.camerashop_be.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "chat_room")
public class ChatRoom  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String customer;

	private String createdAt;

	private String endedAt;

	private String staff;
}
