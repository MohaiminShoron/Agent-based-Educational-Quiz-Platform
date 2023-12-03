package com.seng696.Seng696.dto;

import lombok.Data;

@Data
public class OptionDTO {
    private String text;

    private boolean isCorrect;


    public OptionDTO(String text, boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
    }

}