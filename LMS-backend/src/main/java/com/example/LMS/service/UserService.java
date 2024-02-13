package com.example.LMS.service;

import com.example.LMS.entity.CustomerAudit;
import com.example.LMS.entity.UserEntity;
import com.example.LMS.repository.CustomerAuditRepository;
import com.example.LMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerAuditRepository customerAuditRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserEntity saveUser(UserEntity user) {
        logger.info("Attempting to save user: {}", user.getUsername());
        // Check if the username is already taken
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken");
        }

        // Hash the password before saving
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        // Save the user to the database
        logger.info("User saved successfully: {}", user.getUsername());
        return userRepository.save(user);
    }

    public UserEntity authenticateUser(String username, String password) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            String hashedEnteredPassword = hashPassword(password);
            if (hashedEnteredPassword.equals(user.getPassword())) {
                // Save last login time in customer_audit table
                saveLastLoginTime(user);
                return user;
            }
        }
        return null;
    }

    private void saveLastLoginTime(UserEntity user) {
        CustomerAudit customerAudit = new CustomerAudit();
        customerAudit.setUser(user);
        customerAudit.setLastLoginTime(new Timestamp(System.currentTimeMillis()));

        customerAuditRepository.save(customerAudit);
    }


    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert byte array to hexadecimal representation
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
