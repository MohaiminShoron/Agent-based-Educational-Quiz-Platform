package com.seng696.Seng696.dto;

import lombok.Data;

@Data
public class ScoreDTO {
    private Long userId;
    private String username;
    private Integer scoreValue;
    private Long categoryId;


}
