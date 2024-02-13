package com.example.LMS.controller;

import com.example.LMS.entity.UserEntity;
import com.example.LMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserEntity user) {
        try {
            // Save the user to the database
            UserEntity savedUser = userService.saveUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, UserEntity>> login(@RequestBody UserEntity user) {
        try {
            // Validate user credentials
            UserEntity authenticatedUser = userService.authenticateUser(user.getUsername(), user.getPassword());

            if (authenticatedUser != null) {
                // Implement token generation logic if needed
                String token = generateToken(authenticatedUser);

                // Return token or user details as needed
                Map<String, UserEntity> response = new HashMap<>();
//                response.put("message", "Login successful");
//                response.put("userId", String.valueOf(authenticatedUser.getId()));
//                response.put("user", authenticatedUser);
                response.put("user", authenticatedUser);

                return ResponseEntity.ok(response);
            } else {
//                return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
                Map<String, UserEntity> response = new HashMap<>();
//                response.put("message", "Login unsuccessful");


                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            Map<String, UserEntity> response = new HashMap<>();
            response.put("message", user);

            return ResponseEntity.ok(response);
        }
    }

    private String generateToken(UserEntity user) {
        // Implement token generation logic (e.g., using JWT)
        // This is a simplified example; use a proper token library in a real-world scenario
        return "sampleToken";
    }
}

