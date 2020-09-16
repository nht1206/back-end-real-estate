package com.codegym.service;

import com.codegym.dao.model.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface DirectionService {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void save(Direction direction);
    List<Direction> findAll();
    Direction findById(Long id);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteById(Long id);
}
