package com.example.demo.service;

import com.example.demo.converter.UserConverter;
import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.ERole;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtils;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserConverter userConverter, UserDetailsService userDetailsService, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userConverter = userConverter;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserResponse add(UserDTO userDTO) {
        if (userRepository.findByUserName(userDTO.getUserName()) != null) {
            throw new RuntimeException("Username already exists!");
        }

        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new RuntimeException("Email already exists!");
        }

        List<Role> roles = new ArrayList<>();
        // Duyệt qua danh sách các tên role và tìm kiếm các role trong cơ sở dữ liệu
        userDTO.getRoles().forEach(roleName -> {
            Role foundRole = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(foundRole);
        });

        User user = userConverter.convertToEntity(userDTO);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode("123456"));  // Mật khẩu mặc định
        user.setStatus(1);
        user.setUserName(UUID.randomUUID().toString());

        User userAdded = userRepository.save(user);
        return userConverter.convertToResponse((Page<User>) userAdded);
    }



    @Override
    public Page<UserResponse> getAll(int page, int size) {
        return null;
    }


    @Override
    public void register(UserDTO userDTO) {
        if (userRepository.findByUserName(userDTO.getUserName()) != null) {
            throw new RuntimeException("Username already exists!");
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userConverter.convertToEntity(userDTO);
        Role clientRole = roleRepository.findByName(ERole.CLIENT.name())
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(ERole.CLIENT.name());
                    System.out.println("Roles: " + newRole);
                    user.setRoles(List.of(newRole));
                    return roleRepository.save(newRole);
                });

        user.setRoles(List.of(clientRole));

        user.setStatus(1);
        userDTO.setUserName(user.getUserName());

        userRepository.save(user);
        System.out.println("User created successfully with default CLIENT role.");
    }


    @Override
    public Map<String, String> login(LoginDTO loginDTO) {
        User user = userRepository.findByUserName(loginDTO.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        List<String> roleName = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        String accessToken = jwtUtils.generateToken(user.getUserName(), roleName);
        return Map.of("access_token", accessToken);
    }


    @Override
    public void logout(String username) {
        User user = userRepository.findByUserName(username);
        if (user != null) {
            user.setRefreshToken(null);
            userRepository.save(user);
        }
    }


    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(user.getUserName());
//            userDTO.setPassword(user.getPassword());
            // Map roles to a Set of role IDs
//            List<Long> roleIds = user.getRoles().stream()
//                    .map(Role::getId) // Extract the role ID
//                    .collect(Collectors.toList());
//            userDTO.setRoles(roleIds);

            List<String> roleName = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toList());
            userDTO.setRoles(roleName);

            userDTOs.add(userDTO);
        }

        return userDTOs;
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new RuntimeException("User not found with username: " + username);
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(user.getUserName());

        userDTO.setPassword(user.getPassword());

        List<String> roleName = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        userDTO.setRoles(roleName);

        return userDTO;
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(user.getUserName());

        userDTO.setPassword(user.getPassword());
        List<String> roleName = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        userDTO.setRoles(roleName);
        return userDTO;
    }


    @Override
    public String refreshToken(String refreshToken) {
        // Validate the refresh token
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("Refresh token is required");
        }

        // Extract username from the refresh token
        String username = jwtUtils.extractUsername(refreshToken);

        // Find user by username
        User user = userRepository.findByUserName(username);
        if (user == null || !refreshToken.equals(user.getRefreshToken())) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        // Check if the refresh token is expired
        if (jwtUtils.isTokenExpired(refreshToken)) {
            throw new IllegalArgumentException("Refresh token has expired");
        }

        // Generate a new access token
        var userDetails = userDetailsService.loadUserByUsername(username);
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        // Trả về token mới
        return jwtUtils.generateToken(username, roles);
    }


}
