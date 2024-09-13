package com.photography.lithuanian_press_photography.repository;

import com.photography.lithuanian_press_photography.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository <Category, UUID> {
    Page<Category> findByContestId (UUID contestId, Pageable pageable);
}
