package com.codegym.service;


import com.codegym.dao.model.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface ReplyService {
    Page<Reply> findAll(Pageable pageable);

    Page<Reply> findRepliesByComment(Long commentId, Pageable pageable);

    Reply findById(Long id);

    @PreAuthorize("hasRole('ROLE_USER')")
    void save(Reply reply);

    @PreAuthorize("hasRole('ROLE_USER')")
    void deleteById(Long id);
}
