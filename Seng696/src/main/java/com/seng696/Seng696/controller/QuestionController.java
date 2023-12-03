package com.seng696.Seng696.controller;

import com.seng696.Seng696.dto.QuestionDTO;
import com.seng696.Seng696.entity.Question;
import com.seng696.Seng696.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

//    @GetMapping("/questions/{categoryId}")
//    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable Long categoryId) {
//        List<Question> questions = questionService.getQuestionsByCategory(categoryId);
//        return ResponseEntity.ok(questions);
//    }

    @GetMapping("/questions/{categoryId}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByCategory(@PathVariable Long categoryId) {
        List<QuestionDTO> questionDTOs = questionService.getQuestionsByCategory(categoryId);
        return ResponseEntity.ok(questionDTOs);
    }
}

