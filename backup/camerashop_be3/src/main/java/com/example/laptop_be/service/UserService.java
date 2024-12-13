package com.example.laptop_be.service;

import com.example.laptop_be.converter.Converter;
import com.example.laptop_be.repository.UserRepository;
import com.example.laptop_be.dto.UserDTO;
import com.example.laptop_be.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// UserService
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Converter converter;

    public UserDTO createUser(UserDTO userDTO) {
        User user = converter.convertToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return converter.convertToDto(savedUser);
    }

    public UserDTO getUserById(int id) {
        User user = userRepository.findById(id).orElse(null);
        return converter.convertToDto(user);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    public UserDTO updateUser(int id, UserDTO userDTO) {
        User user = converter.convertToEntity(userDTO);
        user.setIdUser(id);
        User updatedUser = userRepository.save(user);
        return converter.convertToDto(updatedUser);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}