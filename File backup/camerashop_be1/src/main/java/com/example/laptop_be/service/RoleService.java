package com.example.laptop_be.service;

import com.example.laptop_be.dto.RoleDTO;
import com.example.laptop_be.entity.Role;
import com.example.laptop_be.repository.RoleRepository;

import com.example.laptop_be.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// RoleService
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Converter converter;

    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = converter.convertToEntity(roleDTO);
        Role savedRole = roleRepository.save(role);
        return converter.convertToDto(savedRole);
    }

    public RoleDTO getRoleById(int id) {
        Role role = roleRepository.findById(id).orElse(null);
        return converter.convertToDto(role);
    }

    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    public RoleDTO updateRole(int id, RoleDTO roleDTO) {
        Role role = converter.convertToEntity(roleDTO);
        role.setIdRole(id);
        Role updatedRole = roleRepository.save(role);
        return converter.convertToDto(updatedRole);
    }

    public void deleteRole(int id) {
        roleRepository.deleteById(id);
    }
}