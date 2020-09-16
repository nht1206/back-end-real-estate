package com.codegym.dao.repository;

import com.codegym.dao.model.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
