package com.codegym.service;

import com.codegym.dao.model.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface PostTypeService {
    List<PostType> findAll();

    Page<PostType> findAll(Pageable pageable);

    PostType findById(Long id);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    boolean save(PostType postType);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteById(Long id);

    PostType findByName(String inputName);
}
