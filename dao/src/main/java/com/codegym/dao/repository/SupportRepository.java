package com.codegym.dao.repository;

import com.codegym.dao.model.Support;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportRepository extends JpaRepository<Support, Long> {
    Page<Support> getSupportsByReason_Id(Long reasonId, Pageable pageable);
}
