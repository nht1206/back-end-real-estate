package com.codegym.dao.repository;

import com.codegym.dao.model.Reason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReasonRepository extends JpaRepository<Reason, Long> {
}
