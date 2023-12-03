package com.seng696.Seng696.repository;

import com.seng696.Seng696.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Custom query methods can be defined here...
}
