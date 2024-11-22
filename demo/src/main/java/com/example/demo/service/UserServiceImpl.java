package com.example.demo.service;

import com.example.demo.converter.UserConverter;
import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.ERole;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserConverter userConverter, UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userConverter = userConverter;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void addUser(UserDTO userDTO) {
        // Check if the username already exists
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new RuntimeException("Username already exists!");
        }

        // Encode the password
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Convert DTO to entity
        User user = userConverter.convertToEntity(userDTO);

        // Get or create the CLIENT role
        Role clientRole = roleRepository.findByName(ERole.CLIENT.name())
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(ERole.CLIENT.name());
                    return roleRepository.save(newRole);
                });

        // Assign the CLIENT role to the user
        user.setRoles(Set.of(clientRole));

        // Set additional user attributes
        user.setStatus(true);
        user.setUsername(userDTO.getUsername());

        // Save the user
        userRepository.save(user);

        System.out.println("User created successfully with default CLIENT role.");
    }



    @Override
    public Map<String, String> login(LoginDTO loginDTO) {
        // Validate user credentials
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if (user == null || !passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        // Generate tokens
        var userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        String accessToken = jwtUtil.generateToken(userDetails, roles);
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        // Set refresh token and expiry date in the database
        user.setRefreshToken(refreshToken);

        // Set the expiry date to 7 days from now (or the duration of your refresh token validity)
        user.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));
        userRepository.save(user);

        // Return both tokens
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }



    @Override
    public void logout(String username) {
        User user = userRepository.findByUsername(username);
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
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());

            // Map roles to a Set of role IDs
            Set<Long> roleIds = user.getRoles().stream()
                    .map(Role::getId) // Extract the role ID
                    .collect(Collectors.toSet());
            userDTO.setRoles(roleIds);

            userDTOs.add(userDTO);
        }

        return userDTOs;
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found with username: " + username);
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());

        // Map roles to a Set of role IDs
        Set<Long> roleIds = user.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
        userDTO.setRoles(roleIds);

        return userDTO;
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());

        // Map roles to a Set of role IDs
        Set<Long> roleIds = user.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
        userDTO.setRoles(roleIds);

        return userDTO;
    }


    @Override
    public String refreshToken(String refreshToken) {
        // Validate the refresh token
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("Refresh token is required");
        }

        // Extract username from the refresh token
        String username = jwtUtil.extractUsername(refreshToken);

        // Find user by username
        User user = userRepository.findByUsername(username);
        if (user == null || !refreshToken.equals(user.getRefreshToken())) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        // Check if the refresh token is expired
        if (jwtUtil.isTokenExpired(refreshToken)) {
            throw new IllegalArgumentException("Refresh token has expired");
        }

        // Generate a new access token
        var userDetails = userDetailsService.loadUserByUsername(username);
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        return jwtUtil.generateToken(userDetails, roles);
    }

}
