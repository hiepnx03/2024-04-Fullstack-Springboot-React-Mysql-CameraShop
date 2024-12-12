package com.example.demo.service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.response.UserResponse;
import org.springframework.data.domain.Page;

import java.util.*;


public interface UserService {
    public void register(UserDTO userDTO) ;
    public Map<String, String> login(LoginDTO loginDTO);
    public List<UserDTO> getAllUsers() ;
    public String refreshToken(String refreshToken);
    public void logout(String username);
    public UserDTO getUserByUsername(String username);
    UserDTO getUserById(Long id);
    UserResponse add(UserDTO userDTO);
    public void forgotPassword(String email, String siteURL);
    void resetPassword(String verificationCode, String password);
    Boolean verify(String verificationCode);
    Page<UserResponse> getAll(int page, int size);
}
