package com.example.camerashop_be.service;


import com.example.camerashop_be.entity.Role;

import java.util.List;

public interface IRoleService {
    Role getRoleByName(String name);

    List<Role> getRoleByUserId(Long userId);
}
