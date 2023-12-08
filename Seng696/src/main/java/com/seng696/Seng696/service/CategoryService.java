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
//    @Autowired
//    public CategoryService(CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository;
//    }


//    public List<Category> getAllCategories() {
//        return categoryRepository.findAll();
//    }
//
//    public List<Category> getQuizCategories() {
//        final String url = "http://localhost:8000/getCategories";
//        String[] categories = restTemplate.getForObject(url, String[].class);
//
//        List<Category> categoryDTOs = new ArrayList<>();
//        int id = 1;
//        for (String categoryName : categories) {
//            categoryDTOs.add(new Category(id++, categoryName));
//        }
//        return categoryDTOs;
//    }
}
