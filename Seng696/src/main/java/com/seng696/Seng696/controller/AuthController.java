package com.seng696.Seng696.controller;

import com.seng696.Seng696.entity.User;
import com.seng696.Seng696.repository.UserRepository;
import com.seng696.Seng696.security.JwtService;
import com.seng696.Seng696.service.JadeClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


/**
 * The AuthController class is responsible for handling all authentication-related actions.
 * It provides endpoints for user registration and login.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private final JadeClientService jadeClientService;

    public AuthController(JadeClientService jadeClientService) {
        this.jadeClientService = jadeClientService;
    }


    /**
     * Endpoint for user registration.
     * Encrypts the user's password and sends the registration information to the JADE system.
     *
     * @param user The user data for registration.
     * @return A response message indicating the registration result.
     */
    @PostMapping("/user_registration")
    public String registerUser(@RequestBody User user) {
        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return jadeClientService.sendUserRegistrationToJade(user);

    }


    /**
     * Endpoint for user authentication.
     * Verifies the user credentials and generates a JWT token if authentication is successful.
     *
     * @param loginRequest The user login credentials.
     * @return A ResponseEntity containing the user details or an error message.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            String token = jwtService.generateToken(user.getUsername());
            return ResponseEntity.ok(user
            );
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
