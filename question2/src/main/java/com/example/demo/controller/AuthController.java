package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.ErrorResponse;
import com.example.demo.dto.MessageResponse;
import com.example.demo.dto.PasswordHashRequest;
import com.example.demo.dto.PasswordHashResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JwtTokenUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private UserRepository userRepository;

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
//        try {
//            authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                    authRequest.getUsername(), 
//                    authRequest.getPassword()
//                )
//            );
//
//            final UserDetails userDetails = userDetailsService
//                .loadUserByUsername(authRequest.getUsername());
//            final String token = jwtTokenUtil.generateToken(userDetails);
//
//            return ResponseEntity.ok(new AuthResponse(token));
//        } catch (BadCredentialsException e) {
//            return ResponseEntity
//                .status(HttpStatus.UNAUTHORIZED)
//                .body(new ErrorResponse("Invalid username or password"));
//        } catch (Exception e) {
//            return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(new ErrorResponse("Authentication failed: " + e.getMessage()));
//        }
//    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), 
                    authRequest.getPassword()
                )
            );

            final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authRequest.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new AuthResponse(token));
                
        } catch (BadCredentialsException e) {
            ErrorResponse error = new ErrorResponse(
                "Invalid username or password",
                "INVALID_CREDENTIALS",
                System.currentTimeMillis()
            );
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
        }
    }
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping(value = "/hash", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> hashPassword(@Valid @RequestBody PasswordHashRequest request) {
        try {
            String hashedPassword = passwordEncoder.encode(request.getPassword());
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new PasswordHashResponse(hashedPassword));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(
                "Error hashing password",
                "HASH_ERROR",
                System.currentTimeMillis()
            );
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
        }
    }

    // เพิ่ม method สำหรับสร้าง user ใหม่
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        try {
            // ตรวจสอบว่า username ซ้ำหรือไม่
            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ErrorResponse("Username already exists"));
            }

            // สร้าง user ใหม่
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole("ROLE_USER");  // หรือตามที่ระบุใน request
            
            userRepository.save(user);

            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new MessageResponse("User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse("Error registering user"));
        }
    }

    
    
}