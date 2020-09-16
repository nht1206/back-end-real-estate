package com.codegym.dao.repository;

import com.codegym.dao.model.Category;
import com.codegym.dao.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String inputName);
}
