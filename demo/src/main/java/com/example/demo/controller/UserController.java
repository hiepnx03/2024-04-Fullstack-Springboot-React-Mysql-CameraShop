package com.example.demo.controller;


import com.example.demo.dto.UserDTO;
import com.example.demo.dto.request.ChangePasswordRequest;
import com.example.demo.dto.response.ChangePasswordResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.PasswordHistoryService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PasswordHistoryService passwordHistoryService;


    // API to get all users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        try {
            UserDTO user = userService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        try {
            UserDTO userDTO = userService.getUserById(id);
            return ResponseEntity.ok(userDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping("/{userId}/change-password")
    public ResponseEntity<ChangePasswordResponse> changePassword(
            @PathVariable Long userId,
            @RequestBody @Valid ChangePasswordRequest request) {
        String message = passwordHistoryService.changePassword(userId, request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok(new ChangePasswordResponse(message));
    }
}
