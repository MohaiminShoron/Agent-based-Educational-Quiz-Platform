package com.seng696.Seng696.controller;

import com.seng696.Seng696.dto.ScoreDTO;
import com.seng696.Seng696.entity.Score;
import com.seng696.Seng696.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @PostMapping("/scores")
    public ResponseEntity<Score> saveScore(@RequestBody ScoreDTO scoreDTO) {
        Score score = new Score();
        score.setUserId(scoreDTO.getUserId());
        score.setUsername(scoreDTO.getUsername());
        score.setScoreValue(scoreDTO.getScoreValue());
        score.setCategoryId(scoreDTO.getCategoryId());

        Score savedScore = scoreService.saveScore(score);
        return ResponseEntity.ok(savedScore);
    }

    @GetMapping("/leaderboard/{categoryId}")
    public ResponseEntity<List<Score>> getLeaderboard(@PathVariable Long categoryId, @RequestParam(defaultValue = "5") int limit) {
        List<Score> topScores = scoreService.getTopScores(categoryId, limit);
        return ResponseEntity.ok(topScores);
    }
}
