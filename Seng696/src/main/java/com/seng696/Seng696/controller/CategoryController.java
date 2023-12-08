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

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        String url = "http://localhost:8000/getCategories";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getBody() != null) {
            List<String> categoryStrings = Arrays.asList(gson.fromJson(response.getBody(), String[].class));
            List<Category> categories = new ArrayList<>();

            for (String categoryString : categoryStrings) {
                categories.add(gson.fromJson(categoryString, Category.class));
            }

            return ResponseEntity.ok(categories);
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }


//    @GetMapping("/categories")
//    public ResponseEntity<List<Category>> getCategories() {
//        String url = "http://localhost:8000/getCategories";
//
//        // Fetch the response as a String
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//
//        // Check if the response body is not null
//        if (response.getBody() != null) {
//            // Unescape the JSON string
//            String json = StringEscapeUtils.unescapeJson(response.getBody());
//            // Remove quotes at the beginning and the end of the string if present
////            json = json.substring(1, json.length() - 1);
//
//            // Deserialize the unescaped JSON string to a List of Category objects
//            Type listType = new TypeToken<List<Category>>(){}.getType();
//            List<Category> categories = gson.fromJson(json, listType);
//
//            return ResponseEntity.ok(categories);
//        } else {
//            // Return an empty list or an appropriate response entity
//            return ResponseEntity.ok(List.of());
//        }
//    }

//    @GetMapping("/categories")
//    public ResponseEntity<List<Category>> getCategories() {
//        String url = "http://localhost:8000/getCategories";
//
//        ResponseEntity<String[]> response = restTemplate.getForEntity(url, String[].class);
//
//        if (response.getBody() != null) {
//            Type listType = new TypeToken<List<Category>>(){}.getType();
//            List<Category> categories = new Gson().fromJson(Arrays.toString(response.getBody()), listType);
//
//            return ResponseEntity.ok(categories);
//        } else {
//            return ResponseEntity.ok(List.of());
//        }
//    }

//    private final CategoryService categoryService;
//    private final RestTemplate restTemplate;
//
//    public CategoryController(RestTemplateBuilder restTemplateBuilder) {
//        this.restTemplate = restTemplateBuilder.build();
//    }
//
//    @GetMapping("/categories")
//    public ResponseEntity<List<Category>> getCategories() {
//        String url = "http://localhost:8000/getCategories";
//        Gson g = new Gson();
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//        Category[] categories = g.fromJson(response.body(), Category[].class);
//
//
//
//        // Make the request for an array of Category, not a single Category
//        ResponseEntity<Category[]> response = restTemplate.getForEntity(url, Category[].class);
//        List<Category> categories = Arrays.asList(response.getBody()); // Convert the array to a list
//
//        return ResponseEntity.ok(categories);
//    }

//    @GetMapping("/categories")
//    public ResponseEntity<List<Category>> getCategories() {
//        String url = "http://localhost:8000/getCategories";
//
//        ResponseEntity<Category> response = restTemplate.getForEntity(url, Category.class);
//        List<Category> categories = Arrays.asList(response.getBody());
//
//        return ResponseEntity.ok(categories);
//    }
//    @Autowired
//    public CategoryController(CategoryService categoryService) {
//        this.categoryService = categoryService;
//    }

//    @GetMapping("/categories")
//    public ResponseEntity<List<Category>> getCategories() {
//        List<Category> categories = categoryService.getAllCategories();
//        return ResponseEntity.ok(categories);
//    }
}
