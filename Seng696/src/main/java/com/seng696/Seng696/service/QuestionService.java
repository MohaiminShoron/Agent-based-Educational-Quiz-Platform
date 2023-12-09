package com.seng696.Seng696.service;

import com.seng696.Seng696.dto.QuestionDTO;
import com.seng696.Seng696.entity.Question;
import com.seng696.Seng696.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for handling operations related to questions.
 */
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * Retrieves a list of questions for a given category.
     *
     * @param categoryId The ID of the category for which questions are requested.
     * @return A list of QuestionDTOs representing the questions of the specified category.
     */
    public List<QuestionDTO> getQuestionsByCategory(Long categoryId) {
        List<Question> questions = questionRepository.findByCategoryId(categoryId);
        return questions.stream().map(QuestionDTO::new).collect(Collectors.toList());
    }
}
