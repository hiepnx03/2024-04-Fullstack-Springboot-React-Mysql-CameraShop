package com.example.camerashop_be.controller.admin;

import com.example.camerashop_be.dto.UserDTO;
import com.example.camerashop_be.dto.filter.FilterUser;
import com.example.camerashop_be.dto.request.ChangePasswordRequest;
import com.example.camerashop_be.dto.response.UserResponse;
import com.example.camerashop_be.entity.*;
import com.example.camerashop_be.repository.specs.UserSpecification;
import com.example.camerashop_be.security.service.UserDetail;
import com.example.camerashop_be.service.IUserService;
import com.example.camerashop_be.service.impl.RoleService;
import lombok.AllArgsConstructor;
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
//@PreAuthorize("hasAuthority('admin')")
@RequestMapping("/api/admin/user")
public class UserManagerController {
    private final IUserService userService;
    private final RoleService roleService;
    AuthenticationManager authenticationManager;

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> user(@RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            Page<UserResponse> user = userService.getAll(page, size);
            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Get user successfully!", user));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("failed", "Get failed!", ""));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable("id") Long id) {
        try {
            UserResponse user = userService.getById(id);
            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Get user successfully!", user));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("failed", "Get failed!", ""));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> add(@RequestBody UserDTO userDTO) {
        try {
//			Authentication authentication = SecurityContextHolder.getContext()
//					.getAuthentication();
//			UserDetail currentUserLogin =(UserDetail) authentication.getPrincipal();

            UserResponse userResponse = userService.add(userDTO);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Added product successful!", userResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), null));
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<ResponseObject> edit(@RequestBody UserDTO userDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            UserDetail currentUserLogin = (UserDetail) authentication.getPrincipal();
            User userIsEdited = userService.getUserByEmail(userDTO.getEmail());

            if (userIsEdited.getEmail().equals(currentUserLogin.getEmail())
                    && !(currentUserLogin.getStatus().intValue() == userDTO.getStatus().intValue())) {
                throw new RuntimeException("Unable to change status by itself!");
            }
            if (currentUserLogin.getAuthorities().stream().anyMatch(e -> e.toString().equals("admin"))
                    && userIsEdited.getEmail().equals(currentUserLogin.getEmail())
                    && !userDTO.getRoles()[0].equals("admin")
                    && userService.countByRoleName(ERole.ADMIN.getName()) <= 1) {
                throw new RuntimeException("Can't delete the only admin account!");
            }
            if (currentUserLogin.getAuthorities().stream().noneMatch(e -> e.toString().equals("admin")
            ) && userIsEdited.getRoles().get(0).getName().equals("admin")) {
                throw new RuntimeException("You don't have permission to edit admin!");
            }

            UserResponse userResponse = userService.edit(userDTO);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Update product successful!", userResponse));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), null));
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<ResponseObject> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(changePasswordRequest.getEmail(), changePasswordRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetail userDetail = (UserDetail) authentication.getPrincipal();
            if (userDetail == null) {
                return ResponseEntity.badRequest()
                        .body(new ResponseObject("failed", "Invalid password!", ""));
            }
            userService.changePassword(changePasswordRequest.getEmail(), changePasswordRequest.getNewPassword());
            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Changed password successfully!", ""));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("failed", "Invalid password!", ""));
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<ResponseObject> filter(@RequestBody FilterUser filterUser) {
        try {
            UserSpecification userSpecifications = new UserSpecification();
            if (filterUser.getCreatedAt() == null || filterUser.getCreatedAt().size() < 1) {
            } else if (filterUser.getCreatedAt().get(0) == null) {
                userSpecifications.add(new Filter("createdAt", QueryOperator.IN, filterUser.getCreatedAt().get(1)));
            } else if (filterUser.getCreatedAt().get(1) == null) {
                userSpecifications.add(new Filter("createdAt", QueryOperator.IN, filterUser.getCreatedAt().get(0)));
            } else if (filterUser.getCreatedAt().get(0) != null && filterUser.getCreatedAt().get(1) != null) {
                userSpecifications.add(new Filter("createdAt", QueryOperator.IN, filterUser.getCreatedAt()));
            }
            if (filterUser.getRole() != null) {
                userSpecifications.add(new Filter("role", QueryOperator.EQUAL, filterUser.getRole()));
            }
            if (filterUser.getStatus() != null) {
                userSpecifications.add(new Filter("status", QueryOperator.EQUAL, filterUser.getStatus()));
            }
            if (filterUser.getEmail() != null) {
                userSpecifications.add(new Filter("email", QueryOperator.LIKE, filterUser.getEmail()));
            }

            Page<UserResponse> userResponses = userService.filter(userSpecifications, filterUser.getPage(), filterUser.getSize());

            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Get user successfully!", userResponses));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @GetMapping("/total")
    public ResponseEntity<ResponseObject> getTotal() {
        try {
            Integer total = userService.totalOrders();
            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Get total orders successfully!", total));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("ok", e.getMessage(), ""));
        }
    }

    @GetMapping("/total-in-day")
    public ResponseEntity<ResponseObject> getTotalInDay() {
        try {
            Integer total = userService.totalOrdersInDay(new Date());
            return ResponseEntity.ok()
                    .body(new ResponseObject("ok", "Get total orders successfully!", total));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject("ok", e.getMessage(), ""));
        }
    }

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
        return list.stream()
                .flatMap(Pattern.compile(",")::splitAsStream)
                .collect(Collectors.toList());

    }
}
