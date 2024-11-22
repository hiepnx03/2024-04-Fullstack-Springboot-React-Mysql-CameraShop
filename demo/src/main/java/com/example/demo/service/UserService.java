package com.example.demo.service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.*;


public interface UserService {
    public void addUser(UserDTO userDTO) ;
    public void login(LoginDTO loginDTO);
    public List<UserDTO> getAllUsers() ;
}
