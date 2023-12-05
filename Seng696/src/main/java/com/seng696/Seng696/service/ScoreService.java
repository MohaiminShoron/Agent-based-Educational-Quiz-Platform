package com.seng696.Seng696.service;

import com.seng696.Seng696.entity.Score;
import com.seng696.Seng696.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Transactional
    public Score saveScore(Score score) {
        return scoreRepository.save(score);
    }

//    public List<Score> getTopScores(Long categoryId, int limit) {
//        // Assuming you have a method in your repository to find the top scores by category
//        return scoreRepository.findTopScoresByCategory(categoryId, PageRequest.of(0, limit, Sort.by("scoreValue").descending()));
//    }

    public List<Score> getTopScores(Long categoryId, int limit) {
        // Create a PageRequest object with the desired number of results and sort order
        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by("scoreValue").descending());
        // Use the repository method to retrieve the scores
        return scoreRepository.findByCategoryIdOrderByScoreValueDesc(categoryId, pageRequest).getContent();
    }
}
