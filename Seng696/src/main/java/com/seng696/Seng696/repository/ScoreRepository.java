package com.seng696.Seng696.repository;

import com.seng696.Seng696.entity.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    Page<Score> findByCategoryIdOrderByScoreValueDesc(Long categoryId, Pageable pageable);

    // Alternatively, if you want a list instead of a page
//    List<Score> findByCategoryIdOrderByScoreValueDesc(Long categoryId, Pageable pageable);
    // Custom database queries if needed
}
