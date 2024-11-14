package com.example.camerashop_be.repository.chat;

import com.example.camerashop_be.entity.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRoomRepo extends JpaRepository<MessageRoom, Long> {
    List<MessageRoom> findByChatRoomId(Long id);
}
