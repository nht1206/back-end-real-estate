package com.codegym.service;

import com.codegym.dao.model.ERole;
import com.codegym.dao.model.Role;
import org.springframework.security.access.prepost.PreAuthorize;


public interface RoleService {
    Iterable<Role> findAll();

    Role findById(Long id);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void save(Role role);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteById(Long id);

    Role findByRoleName(ERole roleUser);
}
