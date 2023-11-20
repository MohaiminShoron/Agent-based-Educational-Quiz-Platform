package com.seng696.Seng696.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    // other fields like email, etc.

    // standard getters and setters
}