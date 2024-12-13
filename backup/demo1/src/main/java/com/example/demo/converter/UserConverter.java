package com.example.demo.converter;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserConverter {
    private final ModelMapper modelMapper;

    public UserDTO convertToDto(User entity) {
//        if (entity == null) {
//            return null;
//        }
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUsername(entity.getUsername());
//        userDTO.setPassword(entity.getPassword()); // Be cautious about exposing passwords
//
//        userDTO.setRoles(entity.getRoles().stream()
//                .map(Role::getId)
//                .collect(Collectors.toSet()));
//
//        return userDTO;
        return modelMapper.map(entity, UserDTO.class);
    }

    // Convert UserDTO to User entity
    public User convertToEntity(UserDTO userDTO) {
//        if (userDTO == null) {
//            return null;
//        }
//        User user = new User();
//        user.setUsername(userDTO.getUsername());
//        user.setPassword(userDTO.getPassword()); // Usually, passwords should be encrypted/hashed
//
//        Set<Role> roles = userDTO.getRoles().stream()
//                .map(roleId -> {
//                    Role role = new Role();
//                    role.setId(roleId);
//                    return role;
//                })
//                .collect(Collectors.toSet());
//
//        user.setRoles(roles);
//        return user;
        return modelMapper.map(userDTO, User.class);
    }


}
