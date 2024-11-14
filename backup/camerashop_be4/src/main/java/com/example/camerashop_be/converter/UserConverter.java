package com.example.camerashop_be.converter;


import com.example.camerashop_be.dto.UserDTO;
import com.example.camerashop_be.dto.response.UserResponse;
import com.example.camerashop_be.entity.Role;
import com.example.camerashop_be.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserConverter {

	private final ModelMapper modelMapper;

	public UserDTO convertToDto(User entity) {
		return modelMapper.map(entity, UserDTO.class);
	}

	public User convertToEntity(UserDTO dto) {
		return modelMapper.map(dto, User.class);
	}
	public Page<UserDTO> convertToDTO(Page<User> pageEntity) {
		if (pageEntity == null) {
			return null;
		}
		return pageEntity.map(this::convertToDto);
	}
	public UserResponse convertToResponse(User entity) {
		UserResponse userResponse=modelMapper.map(entity, UserResponse.class);
		List<String> role =entity.getRoles().stream().map(Role::getName).collect(Collectors.toList());
		userResponse.setRoles(role);
		return userResponse;
	}

	public User convertToEntity(UserResponse response) {
		return modelMapper.map(response, User.class);
	}
	public Page<UserResponse> convertToResponse(Page<User> pageEntity) {
		if (pageEntity == null) {
			return null;
		}
		return pageEntity.map(this::convertToResponse);
	}
}
