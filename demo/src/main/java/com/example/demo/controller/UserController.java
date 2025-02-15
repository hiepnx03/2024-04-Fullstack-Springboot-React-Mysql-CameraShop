package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.request.ChangePasswordRequest;
import com.example.demo.dto.response.ChangePasswordResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.PasswordHistoryService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final PasswordHistoryService passwordHistoryService;

    @Operation(summary = "Get all users", description = "Retrieve all users in the system.")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all users")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.info("Fetching all users");
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get user by username", description = "Retrieve user details by their username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/by-username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        try {
            log.info("Fetching user with username: {}", username);
            UserDTO user = userService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            log.error("User with username {} not found", username, e);
            return ResponseEntity.status(404).body(null);
        }
    }

    @Operation(summary = "Get user by ID", description = "Retrieve user details by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        try {
            log.info("Fetching user with ID: {}", id);
            UserDTO userDTO = userService.getUserById(id);
            return ResponseEntity.ok(userDTO);
        } catch (RuntimeException e) {
            log.error("User with ID {} not found", id, e);
            return ResponseEntity.status(404).body(null);
        }
    }

    @Operation(summary = "Change password for user", description = "Change the password for a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Password change failed")
    })
    @PostMapping("/{userId}/change-password")
    public ResponseEntity<ChangePasswordResponse> changePassword(
            @PathVariable Long userId,
            @RequestBody @Valid ChangePasswordRequest request) {
        log.info("Changing password for user ID: {}", userId);
        String message = passwordHistoryService.changePassword(userId, request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok(new ChangePasswordResponse(message));
    }
}
