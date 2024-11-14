package com.example.camerashop_be.service.impl;

import com.example.camerashop_be.entity.Role;
import com.example.camerashop_be.repository.RoleRepo;
import com.example.camerashop_be.service.IRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleService implements IRoleService {
    private final RoleRepo roleRepo;

    @Override
    public Role getRoleByName(String name) {
        return null;
    }

    @Override
    public List<Role> getRoleByUserId(Long userId) {
        List<Role> roles = roleRepo.findAllByUserId(userId);
        return roles;
    }
}
