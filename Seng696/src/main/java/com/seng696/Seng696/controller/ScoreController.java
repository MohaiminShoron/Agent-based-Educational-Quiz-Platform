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

//    @PostMapping("/scores")
//    public ResponseEntity<Score> saveScore(@RequestBody ScoreDTO scoreDTO) {
//        Score score = new Score();
//        score.setUserId(scoreDTO.getUserId());
//        score.setUsername(scoreDTO.getUsername());
//        score.setScoreValue(scoreDTO.getScoreValue());
//        score.setCategoryId(scoreDTO.getCategoryId());
//
//        Score savedScore = scoreService.saveScore(score);
//        return ResponseEntity.ok(savedScore);
//    }

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


    @GetMapping("/leaderboard/{categoryId}")
    public ResponseEntity<List<Score>> getLeaderboard(@PathVariable Long categoryId, @RequestParam(defaultValue = "5") int limit) {
        List<Score> topScores = scoreService.getTopScores(categoryId, limit);
        return ResponseEntity.ok(topScores);
    }
}
