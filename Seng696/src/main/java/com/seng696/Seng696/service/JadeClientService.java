package com.seng696.Seng696.service;

import com.seng696.Seng696.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * This service is responsible for interacting with the JADE agent system via HTTP.
 * It provides functionality to send data to JADE agents and receive responses.
 */
@Service
public class JadeClientService {

    private final RestTemplate restTemplate;

    public JadeClientService() {
        this.restTemplate = new RestTemplate();
    }

    public String sendUserRegistrationToJade(User userRegistration) {
        final String url = "http://localhost:8000/registerUser"; // JADE HTTP server endpoint
        return restTemplate.postForObject(url, userRegistration, String.class);
    }
}

