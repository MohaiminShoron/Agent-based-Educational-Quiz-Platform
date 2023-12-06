package com.seng696.Seng696.controller;

import com.seng696.Seng696.entity.User;
import com.seng696.Seng696.repository.UserRepository;
import com.seng696.Seng696.security.JwtService;
import com.seng696.Seng696.service.JadeClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/user_registration")
    public String registerUser(@RequestBody User user) {
        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return jadeClientService.sendUserRegistrationToJade(user);
//        userRepository.save(user);
//        return ResponseEntity.ok("User registered successfully");
    }
//    @PostMapping("/user_registration")
//    public ResponseEntity<?> registerUser(@RequestBody User user) {
//        // Encrypt the password
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        jadeClientService.sendUserRegistrationToJade(user);
////        userRepository.save(user);
//        return ResponseEntity.ok("User registered successfully");
//    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            String token = jwtService.generateToken(user.getUsername());
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
