package com.codegym.service;

import com.codegym.dao.model.Support;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface SupportService {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Page<Support> findAll(Pageable pageable);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Support findById(Long id);

    void save(Support support);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteById(Long id);

    Page<Support> findByReasonId(Long reasonId, Pageable pageable);
}
