package com.example.camerashop_be.service.impl;

import com.example.camerashop_be.entity.MessageRoom;
import com.example.camerashop_be.repository.chat.MessageRoomRepo;
import com.example.camerashop_be.service.IMessageRoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class MessageRoomService implements IMessageRoomService {
	private final MessageRoomRepo messageRoomRepo;
	@Override
	public List<MessageRoom> getAll() {
		return messageRoomRepo.findAll();
	}
}
