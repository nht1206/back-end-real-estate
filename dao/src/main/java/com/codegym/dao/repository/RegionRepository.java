package com.codegym.dao.repository;

import com.codegym.dao.model.Category;
import com.codegym.dao.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
    Region findByName(String inputName);
}
