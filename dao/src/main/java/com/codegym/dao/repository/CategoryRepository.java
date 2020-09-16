package com.codegym.dao.repository;

import com.codegym.dao.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String inputName);
}
