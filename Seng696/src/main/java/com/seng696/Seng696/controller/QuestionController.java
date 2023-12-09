package com.seng696.Seng696.controller;

import com.seng696.Seng696.dto.QuestionDTO;
import com.seng696.Seng696.entity.Question;
import com.seng696.Seng696.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling question-related requests.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }


    /**
     * Endpoint to get questions by category.
     * Retrieves a list of questions for a specific category.
     *
     * @param categoryId The ID of the category to retrieve questions for.
     * @return A ResponseEntity containing a list of QuestionDTOs for the specified category.
     */
    @GetMapping("/questions/{categoryId}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByCategory(@PathVariable Long categoryId) {
        List<QuestionDTO> questionDTOs = questionService.getQuestionsByCategory(categoryId);
        return ResponseEntity.ok(questionDTOs);
    }
}

