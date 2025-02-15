package com.example.demo.controller.admin;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.ResponseObject;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/admin/user")
@Slf4j
public class UserManagerController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/all")
    @Operation(summary = "Fetch all users", description = "Fetch all users with pagination")
    @ApiResponse(responseCode = "200", description = "Users fetched successfully")
    @ApiResponse(responseCode = "400", description = "Error fetching users")
    public ResponseEntity<ResponseObject> user(@RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("Fetching all users, page: {}, size: {}", page, size);
        try {
            Page<UserResponse> user = userService.getAll(page, size);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get user successfully!", user));
        } catch (Exception e) {
            log.error("Error fetching users", e);
            return ResponseEntity.badRequest().body(new ResponseObject("failed", "Get failed!", ""));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Fetch a user by their ID")
    @ApiResponse(responseCode = "200", description = "User fetched successfully")
    @ApiResponse(responseCode = "400", description = "User not found or error")
    public ResponseEntity<ResponseObject> getById(@PathVariable("id") Long id) {
        log.info("Fetching user with ID: {}", id);
        try {
            UserDTO user = userService.getUserById(id);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get user successfully!", user));
        } catch (Exception e) {
            log.error("Error fetching user with ID: {}", id, e);
            return ResponseEntity.badRequest().body(new ResponseObject("failed", "Get failed!", ""));
        }
    }

    @PostMapping("/add")
    @Operation(summary = "Add new user", description = "Add a new user to the system")
    @ApiResponse(responseCode = "200", description = "User added successfully")
    @ApiResponse(responseCode = "400", description = "Error adding user")
    public ResponseEntity<ResponseObject> add(@RequestBody UserDTO userDTO) {
        log.info("Adding new user: {}", userDTO.getEmail());
        try {
            UserResponse userResponse = userService.add(userDTO);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Added product successful!", userResponse));
        } catch (Exception e) {
            log.error("Error adding user: {}", userDTO.getEmail(), e);
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), null));
        }
    }

//    @PutMapping("/edit")
//    @Operation(summary = "Edit user", description = "Edit the details of an existing user")
//    @ApiResponse(responseCode = "200", description = "User edited successfully")
//    @ApiResponse(responseCode = "400", description = "Error editing user")
//    public ResponseEntity<ResponseObject> edit(@RequestBody UserDTO userDTO) {
//        log.info("Editing user: {}", userDTO.getEmail());
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            UserDetail currentUserLogin = (UserDetail) authentication.getPrincipal();
//            User userIsEdited = userService.getUserByEmail(userDTO.getEmail());
//
//            if (userIsEdited.getEmail().equals(currentUserLogin.getEmail()) && !(currentUserLogin.getStatus().intValue() == userDTO.getStatus().intValue())) {
//                throw new RuntimeException("Unable to change status by itself!");
//            }
//            if (currentUserLogin.getAuthorities().stream().anyMatch(e -> e.toString().equals("admin")) && userIsEdited.getEmail().equals(currentUserLogin.getEmail()) && !userDTO.getRoles()[0].equals("admin") && userService.countByRoleName(ERole.ADMIN.getName()) <= 1) {
//                throw new RuntimeException("Can't delete the only admin account!");
//            }
//            if (currentUserLogin.getAuthorities().stream().noneMatch(e -> e.toString().equals("admin")) && userIsEdited.getRoles().get(0).getName().equals("admin")) {
//                throw new RuntimeException("You don't have permission to edit admin!");
//            }
//            UserResponse userResponse = userService.edit(userDTO);
//            return ResponseEntity.ok().body(new ResponseObject("ok", "Update product successful!", userResponse));
//        } catch (Exception e) {
//            log.error("Error editing user: {}", userDTO.getEmail(), e);
//            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), null));
//        }
//    }
//
//    @PostMapping("/change-password")
//    @Operation(summary = "Change user password", description = "Change the password of an existing user")
//    @ApiResponse(responseCode = "200", description = "Password changed successfully")
//    @ApiResponse(responseCode = "400", description = "Invalid password")
//    public ResponseEntity<ResponseObject> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
//        log.info("Changing password for user: {}", changePasswordRequest.getEmail());
//        try {
//            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(changePasswordRequest.getEmail(), changePasswordRequest.getPassword()));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            UserDetail userDetail = (UserDetail) authentication.getPrincipal();
//            if (userDetail == null) {
//                return ResponseEntity.badRequest().body(new ResponseObject("failed", "Invalid password!", ""));
//            }
//            userService.changePassword(changePasswordRequest.getEmail(), changePasswordRequest.getNewPassword());
//            return ResponseEntity.ok().body(new ResponseObject("ok", "Changed password successfully!", ""));
//        } catch (BadCredentialsException e) {
//            log.error("Invalid credentials for user: {}", changePasswordRequest.getEmail(), e);
//            return ResponseEntity.badRequest().body(new ResponseObject("failed", "Invalid password!", ""));
//        }
//    }
//
//    @PostMapping("/filter")
//    @Operation(summary = "Filter users", description = "Filter users by multiple criteria")
//    @ApiResponse(responseCode = "200", description = "Users filtered successfully")
//    @ApiResponse(responseCode = "400", description = "Error filtering users")
//    public ResponseEntity<ResponseObject> filter(@RequestBody FilterUser filterUser) {
//        log.info("Filtering users with criteria: {}", filterUser);
//        try {
//            UserSpecification userSpecifications = new UserSpecification();
//            if (filterUser.getCreatedAt() != null && !filterUser.getCreatedAt().isEmpty()) {
//                userSpecifications.add(new Filter("createdAt", QueryOperator.IN, filterUser.getCreatedAt()));
//            }
//            if (filterUser.getRole() != null) {
//                userSpecifications.add(new Filter("role", QueryOperator.EQUAL, filterUser.getRole()));
//            }
//            if (filterUser.getStatus() != null) {
//                userSpecifications.add(new Filter("status", QueryOperator.EQUAL, filterUser.getStatus()));
//            }
//            if (filterUser.getEmail() != null) {
//                userSpecifications.add(new Filter("email", QueryOperator.LIKE, filterUser.getEmail()));
//            }
//
//            Page<UserResponse> userResponses = userService.filter(userSpecifications, filterUser.getPage(), filterUser.getSize());
//
//            return ResponseEntity.ok().body(new ResponseObject("ok", "Get user successfully!", userResponses));
//        } catch (Exception e) {
//            log.error("Error filtering users", e);
//            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
//        }
//    }
//
//    @GetMapping("/total")
//    @Operation(summary = "Get total number of users", description = "Fetch the total number of users")
//    @ApiResponse(responseCode = "200", description = "Total number of users fetched successfully")
//    @ApiResponse(responseCode = "400", description = "Error fetching total users")
//    public ResponseEntity<ResponseObject> getTotal() {
//        log.info("Fetching total number of users");
//        try {
//            Integer total = userService.totalOrders();
//            return ResponseEntity.ok().body(new ResponseObject("ok", "Get total orders successfully!", total));
//        } catch (Exception e) {
//            log.error("Error fetching total users", e);
//            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
//        }
//    }
//
//    @GetMapping("/total-in-day")
//    @Operation(summary = "Get total number of users in a day", description = "Fetch the total number of users registered in a specific day")
//    @ApiResponse(responseCode = "200", description = "Total number of users in a day fetched successfully")
//    @ApiResponse(responseCode = "400", description = "Error fetching total users in a day")
//    public ResponseEntity<ResponseObject> getTotalInDay() {
//        log.info("Fetching total number of users in the current day");
//        try {
//            Integer total = userService.totalOrdersInDay(new Date());
//            return ResponseEntity.ok().body(new ResponseObject("ok", "Get total orders successfully!", total));
//        } catch (Exception e) {
//            log.error("Error fetching total users in a day", e);
//            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
//        }
//    }

    public List<String> getFields(Object o) {
        List<String> fields = new ArrayList<>();
        Class<?> clazz = o.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            fields.add(field.getName());
        }
        return fields;
    }

    public static boolean isNumber(String text) {
        return text.chars().allMatch(Character::isDigit);
    }

    private String convertWithoutUnderStoke(String str) {
        return str.split("_")[0];
    }

    private List<String> splitCommon(List<String> list) {
        return list.stream().flatMap(Pattern.compile(",")::splitAsStream).collect(Collectors.toList());
    }
}
