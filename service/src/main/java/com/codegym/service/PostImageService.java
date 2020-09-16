package com.codegym.service;

import com.codegym.dao.model.PostImage;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Set;

public interface PostImageService {
    List<PostImage> findAll();
    PostImage findById(Long id);
    Set<PostImage> findByPostId(Long id);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void save(PostImage postImage);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteById(Long id);
}
