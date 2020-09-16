package com.codegym.dao.repository;

import com.codegym.dao.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findCommentsByPost_IdOrderByCreatedAtDesc(Long id, Pageable pageable);
}
