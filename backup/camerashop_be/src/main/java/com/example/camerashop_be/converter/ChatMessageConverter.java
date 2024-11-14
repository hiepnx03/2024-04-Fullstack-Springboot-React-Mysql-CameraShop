package com.example.camerashop_be.converter;

import com.example.camerashop_be.dto.request.MessageRoomRequest;
import com.example.camerashop_be.entity.MessageRoom;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ChatMessageConverter {
	private final ModelMapper modelMapper;
	public MessageRoomRequest convertToRequest(MessageRoom entity) {
		return modelMapper.map(entity, MessageRoomRequest.class);
	}
	public MessageRoom convertToEntity(MessageRoomRequest dto) {
		return modelMapper.map(dto, MessageRoom.class);
	}
}
