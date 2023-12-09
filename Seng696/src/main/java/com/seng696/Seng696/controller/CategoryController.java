package com.seng696.Seng696.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seng696.Seng696.entity.Category;
import com.seng696.Seng696.service.CategoryService;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Controller for handling category-related requests.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class CategoryController {

    private final RestTemplate restTemplate;
    private final Gson gson;

    public CategoryController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.gson = new Gson();
    }

    /**
     * Endpoint to get all categories.
     * Retrieves categories from the JADE agent system and returns them.
     *
     * @return A ResponseEntity containing a list of categories or an empty list if none are found.
     */
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        String url = "http://localhost:8000/getCategories";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getBody() != null) {
            // Deserialize the response body into an array of category JSON strings
            List<String> categoryStrings = Arrays.asList(gson.fromJson(response.getBody(), String[].class));
            List<Category> categories = new ArrayList<>();

            for (String categoryString : categoryStrings) {
                // Deserialize each JSON string into a Category object and add it to the list
                categories.add(gson.fromJson(categoryString, Category.class));
            }

            return ResponseEntity.ok(categories);
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

}
