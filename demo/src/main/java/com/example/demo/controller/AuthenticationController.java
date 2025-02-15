package com.example.demo.controller;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.request.ResetPasswordRequest;
import com.example.demo.entity.ResponseObject;
import com.example.demo.util.JwtUtils;
import com.example.demo.util.TokenBlacklist;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthenticationController {

    private final String frontendUrl;
    private final UserService userService;
    private final TokenBlacklist tokenBlacklist;
    private final JwtUtils jwtUtils;

    @Operation(summary = "User login", description = "Authenticate user and return JWT tokens")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Map<String, String> tokens = userService.login(loginRequest);
            return ResponseEntity.ok(new ResponseObject("ok", "Login successful", tokens));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseObject("failed", "An error occurred", ""));
        }
    }

    @Operation(summary = "Refresh JWT token", description = "Generate a new access token using refresh token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid refresh token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        try {
            String refreshToken = request.get("refreshToken");
            String newAccessToken = userService.refreshToken(refreshToken);
            return ResponseEntity.ok(new ResponseObject("ok", "Token refreshed successfully", Map.of("access_token", newAccessToken)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseObject("failed", "An error occurred", ""));
        }
    }

    @Operation(summary = "User logout", description = "Invalidate the current JWT token")
    @ApiResponse(responseCode = "200", description = "Logged out successfully")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            tokenBlacklist.addToBlacklist(token);
        }
        return ResponseEntity.ok(new ResponseObject("ok", "Logged out successfully", ""));
    }

    @Operation(summary = "User registration", description = "Register a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registration successful"),
            @ApiResponse(responseCode = "400", description = "Invalid user data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    public ResponseEntity<?> processRegister(@RequestBody UserDTO userDTO) {
        try {
            userService.register(userDTO);
            return ResponseEntity.ok(new ResponseObject("ok", "Register successful. Please check your mail to verify!", userDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @Operation(summary = "Forgot password", description = "Send password reset link to user email")
    @ApiResponse(responseCode = "200", description = "Reset email sent")
    @GetMapping("/forgot-password")
    public ResponseEntity<ResponseObject> processForgotPassword(@RequestParam String email) {
        try {
            userService.forgotPassword(email, frontendUrl);
            return ResponseEntity.ok(new ResponseObject("ok", "Sent mail to reset password", "Please check mail to reset password!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @Operation(summary = "Reset password", description = "Reset user password with verification code")
    @ApiResponse(responseCode = "200", description = "Password reset successful")
    @PostMapping("/reset-password")
    public ResponseEntity<ResponseObject> changePassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        try {
            userService.resetPassword(resetPasswordRequest.getVerifyCode(), resetPasswordRequest.getPassword());
            return ResponseEntity.ok(new ResponseObject("ok", "Reset password successful!", ""));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @Operation(summary = "Verify user email", description = "Verify user registration via email verification code")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Verification successful"),
            @ApiResponse(responseCode = "400", description = "Verification failed")
    })
    @GetMapping("/verify")
    public ResponseEntity<ResponseObject> verifyUser(@RequestParam("code") String code) {
        if (userService.verify(code)) {
            return ResponseEntity.ok(new ResponseObject("ok", "Successful verification!", ""));
        } else {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", "Verification failed!", ""));
        }
    }
}
