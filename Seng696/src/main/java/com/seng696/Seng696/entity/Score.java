package com.seng696.Seng696.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "scores")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String username;
    private Integer scoreValue;
    private Long categoryId;

    // Constructors, Getters, and Setters...
}
