package com.seng696.Seng696.service;

import com.seng696.Seng696.entity.Category;
import com.seng696.Seng696.entity.User;
import com.seng696.Seng696.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

//    private final CategoryRepository categoryRepository;

    private final RestTemplate restTemplate;

    public CategoryService() {
        this.restTemplate = new RestTemplate();
    }

    public String sendUserRegistrationToJade(User userRegistration) {
        final String url = "http://localhost:8000/registerUser"; // JADE HTTP server endpoint
        return restTemplate.postForObject(url, userRegistration, String.class);
    }

}
