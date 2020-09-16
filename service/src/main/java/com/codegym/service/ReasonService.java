package com.codegym.service;

import com.codegym.dao.model.Reason;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ReasonService {
    void save(Reason reason);
    List<Reason> findAll();
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Reason findById(Long id);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void delete(Long id);
}
