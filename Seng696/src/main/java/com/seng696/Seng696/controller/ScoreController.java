package com.seng696.Seng696.controller;

import com.google.gson.Gson;
import com.seng696.Seng696.dto.ScoreDTO;
import com.seng696.Seng696.entity.Score;
import com.seng696.Seng696.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * The ScoreController class is responsible for handling all score-related API endpoints.
 * It provides endpoints for saving a score and retrieving the leaderboard.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ScoreController {

    private final RestTemplate restTemplate;
    private final ScoreService scoreService;

    public ScoreController(RestTemplateBuilder restTemplateBuilder, ScoreService scoreService) {
        this.restTemplate = restTemplateBuilder.build();
        this.scoreService = scoreService;
    }

    /**
     * Endpoint to save a score.
     * It receives a ScoreDTO object, sends it to the JADE HttpServerAgent, and returns a response.
     *
     * @param scoreDTO The score data transfer object containing score details.
     * @return A string message indicating the outcome of the save operation.
     */
    @PostMapping("/scores")
    public String saveScore(@RequestBody ScoreDTO scoreDTO) {
        // Send scoreDTO to the JADE HttpServerAgent
        String url = "http://localhost:8000/saveScore";
        ResponseEntity<String> response = restTemplate.postForEntity(url, scoreDTO, String.class);
        if(response.getBody().equals("Score Received")){
            return "Scored saved successfully";
        }

        else
            return "Score saving failed";


    }


    /**
     * Endpoint to get the leaderboard for a specific category.
     * It retrieves the top scores for the category based on a limit and returns them.
     *
     * @param categoryId The ID of the category for which the leaderboard is requested.
     * @param limit The maximum number of top scores to return.
     * @return A response entity containing the list of top scores.
     */
    @GetMapping("/leaderboard/{categoryId}")
    public ResponseEntity<List<Score>> getLeaderboard(@PathVariable Long categoryId, @RequestParam(defaultValue = "5") int limit) {
        List<Score> topScores = scoreService.getTopScores(categoryId, limit);
        return ResponseEntity.ok(topScores);
    }
}
