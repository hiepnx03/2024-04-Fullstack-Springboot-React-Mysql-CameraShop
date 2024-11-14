package com.example.laptop_be.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleDTO {
    private int idRole;
    private String nameRole;
    private List<UserDTO> listUsers;
}