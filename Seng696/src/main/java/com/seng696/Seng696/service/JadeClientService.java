package com.seng696.Seng696.service;

import com.seng696.Seng696.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

