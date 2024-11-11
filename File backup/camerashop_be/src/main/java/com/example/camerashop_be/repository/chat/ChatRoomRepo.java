package com.example.camerashop_be.repository.chat;

import com.example.camerashop_be.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepo extends JpaRepository<ChatRoom, Long> {
}
