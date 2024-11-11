package com.example.camerashop_be.service.impl;

import com.example.camerashop_be.entity.ChatRoom;
import com.example.camerashop_be.repository.chat.ChatRoomRepo;
import com.example.camerashop_be.service.IChatRoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ChatRoomService implements IChatRoomService {
    private final ChatRoomRepo chatRoomRepo;

    @Override
    public List<ChatRoom> getAll() {
        return chatRoomRepo.findAll();
    }
}
