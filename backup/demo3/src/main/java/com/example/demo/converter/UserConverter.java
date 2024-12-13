package com.example.demo.converter;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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



    public UserResponse convertToResponse(Page<User> entity) {
        // Chuyển đổi page thành danh sách userDTO
        List<User> users = entity.getContent();

        // Sử dụng ModelMapper để chuyển đổi User thành UserResponse (có thể là một đối tượng chung cho các user)
        UserResponse userResponse = new UserResponse();
        List<String> roleNames = new ArrayList<>();

        // Duyệt qua tất cả User trong Page để lấy danh sách vai trò
        for (User user : users) {
            // Lấy tên vai trò từ user và thêm vào danh sách roleNames
            List<String> roles = user.getRoles().stream()
                    .map(Role::getName)
                    .toList();
            roleNames.addAll(roles);  // Thêm tất cả các vai trò của từng user vào danh sách
        }

        // Cập nhật các vai trò vào userResponse
        userResponse.setRoles(roleNames);

        // Trả về đối tượng UserResponse đã được chuyển đổi
        return userResponse;
    }


}
