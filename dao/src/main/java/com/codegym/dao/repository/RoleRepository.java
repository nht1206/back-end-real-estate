package com.codegym.dao.repository;

import com.codegym.dao.model.ERole;
import com.codegym.dao.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ERole name);

    Role findByRoleNameIs(ERole user);
}
