package com.example.demo.service;

import com.example.demo.converter.UserConverter;
import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.ERole;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;

    public void addUser(UserDTO userDTO) {
        try {
            // Kiểm tra xem username có tồn tại hay không
            User userCheck = userRepository.findByUsername(userDTO.getUsername());
            if (userCheck != null) {
                throw new RuntimeException("Email already exists! Checkout your mail to verify!");
            }

            // Mã hóa mật khẩu
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            // Chuyển đổi từ UserDTO sang User entity
            User user = userConverter.convertToEntity(userDTO);

            // Tìm role CLIENT trong database
            Role role = roleRepository.findByName(ERole.CLIENT.name());
            if (role == null) {
                role = new Role();
                role.setName(ERole.CLIENT.name());
                roleRepository.save(role); // Lưu role mới vào cơ sở dữ liệu nếu chưa có
            }

            // Set role CLIENT cho user
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles); // Gán role CLIENT cho user

            // Thiết lập thông tin khác cho user
            user.setStatus(true);
//            user.setExpiryDate(Instant.now());
            user.setUsername(userDTO.getUsername());

            // Lưu user vào cơ sở dữ liệu
            userRepository.save(user);

            // Log thông báo thành công
            System.out.println("User created successfully!");

        } catch (RuntimeException e) {
            // Bắt các lỗi RuntimeException, ví dụ như Email đã tồn tại
            System.err.println("Error during user creation: " + e.getMessage());
            throw new RuntimeException("Failed to create user: " + e.getMessage());

        } catch (Exception e) {
            // Bắt tất cả các lỗi khác (ví dụ lỗi cơ sở dữ liệu)
            System.err.println("Unexpected error: " + e.getMessage());
            throw new RuntimeException("Unexpected error occurred while creating user.");
        }
    }

    public void login(LoginDTO loginDTO) {

    }


    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(passwordEncoder.encode(user.getPassword()));
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return (UserDetails) user; // Since User implements UserDetails
    }
}
