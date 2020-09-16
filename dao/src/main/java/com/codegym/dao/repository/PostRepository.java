package com.codegym.dao.repository;

import com.codegym.dao.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    Page<Post> getPostsByUser_Id(Long userId, Pageable pageable);

    Page<Post> getPostsByUser_IdAndTitleContaining(Long userId, String title, Pageable pageable);

    @Query(value = "SELECT * FROM real_estate.post order by view_count desc, created_at asc limit 10",
            nativeQuery = true)
    List<Post> findByViewCount();

    Iterable<Post> findByCategory_Id(Long categoryId);

    Iterable<Post> findByPostType_Id(Long postTypeId);

    Iterable<Post> findByRegion_Id(Long regionId);
}
