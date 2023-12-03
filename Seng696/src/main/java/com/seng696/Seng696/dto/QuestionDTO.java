package com.seng696.Seng696.dto;

import com.seng696.Seng696.entity.Question;
import com.seng696.Seng696.dto.OptionDTO;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class QuestionDTO {
    private Long id;
    private String text;
    private List<OptionDTO> options;

    public QuestionDTO(Long id, String text, List<OptionDTO> options) {
        this.id = id;
        this.text = text;
        this.options = options;
    }

    public QuestionDTO(Question question) {
        this.id = question.getId();
        this.text = question.getText();
        this.options = question.getOptions().stream()
                .map(option -> new OptionDTO(option.getText(), option.isCorrect()))
                .collect(Collectors.toList());
    }

}