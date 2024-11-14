package com.example.demo.controller;

//import com.example.demo.config.JwtUtil;
import com.example.demo.dto.JwtResponse; // Thêm import cho JwtResponse
import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.ResponseObject;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            loginDTO.setUsername(loginDTO.getUsername());
            loginDTO.setPassword(loginDTO.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Register successful. Please check your mail to verify!", loginDTO.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> processRegister(@RequestBody UserDTO user) {
        try {
            userService.addUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Register successful. Please check your mail to verify!", user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDTO userDTO) throws Exception {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
//            );
//        } catch (BadCredentialsException e) {
//            throw new Exception("Incorrect username or password", e);
//        }
//
//        final UserDetails userDetails = userService.loadUserByUsername(userDTO.getUsername());
//        final String jwt = jwtUtil.generateToken(userDetails);
//
//        return ResponseEntity.ok(new JwtResponse(jwt));
//    }
}
