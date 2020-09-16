package com.codegym.service;


import com.codegym.dao.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface CategoryService {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    boolean save(Category category);

    List<Category> findAll();

    Page<Category> findAll(Pageable pageable);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Category findById(Long id);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteById(Long id);

    Category findByName(String inputName);
}
