package com.seng696.Seng696.service;

import com.seng696.Seng696.dto.QuestionDTO;
import com.seng696.Seng696.entity.Question;
import com.seng696.Seng696.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

//    public List<Question> getQuestionsByCategory(Long categoryId) {
//        // Fetch 10 questions for the given category
//        // This example assumes there is a 'category' field in the Question entity
//        return questionRepository.findByCategoryId(categoryId).stream()
//                .limit(5)
//                .collect(Collectors.toList());
//    }


    public List<QuestionDTO> getQuestionsByCategory(Long categoryId) {
        List<Question> questions = questionRepository.findByCategoryId(categoryId);
        return questions.stream().map(QuestionDTO::new).collect(Collectors.toList());
    }
}
