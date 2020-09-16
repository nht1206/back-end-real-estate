package com.codegym.service;

import com.codegym.dao.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface CommentService {
    @PreAuthorize("hasRole('ROLE_USER')")
    void save(Comment comment);
    Page<Comment> findAll(Pageable pageable);
    Page<Comment> findByPostId(Long id, Pageable pageable);
    @PreAuthorize("hasRole('ROLE_USER')")
    Comment findById(Long id);
    @PreAuthorize("hasRole('ROLE_USER')")
    void deleteById(Long id);
}
