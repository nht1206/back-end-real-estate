package com.codegym.service;

import com.codegym.dao.model.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface RegionService {
    List<Region> findAll();

    Page<Region> findAll(Pageable pageable);

    Region findById(Long id);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    boolean save(Region region);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteById(Long id);

    Region findByName(String inputName);
}
