package com.codegym.service;

import com.codegym.dao.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface PostService {
    Page<Post> findAll(Pageable pageable);

    Post findById(Long id);

    @PreAuthorize("hasRole('ROLE_USER')")
    void save(Post post);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteById(Long id);

    Page<Post> findPostsByUserId(Long userId, Pageable pageable);

    Page<Post> findAllBySearchModal(
            Pageable pageable,
            Long categoryId,
            Long regionId,
            Long postTypeId,
            Boolean condition,
            Double area,
            Long price,
            Boolean deal,
            Long directionId,
            String keyword,
            Boolean customerType,
            String direction
    );

    @PreAuthorize("hasRole('ROLE_USER')")
    Page<Post> findPostsByUser_IdAndTitleContaining(Long userId, String title, Pageable pageable);

    List<Post> findByViewCount();

    Iterable<Post> findByCategory_Id(Long categoryId);

    Iterable<Post> findByPostType_Id(Long postTypeId);

    Iterable<Post> findByRegion_Id(Long regionId);

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOD')")
    Page<Post> findPendingPosts(String keyword, Pageable pageable);
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOD')")
    Page<Post> searchApprovedPosts(String keyword, Pageable pageable);

    void updatePostViewCount(Post post);
}
