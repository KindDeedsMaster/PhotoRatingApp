package com.photography.lithuanian_press_photography.repository;

import com.photography.lithuanian_press_photography.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ContestRepository extends JpaRepository <Contest, UUID>, JpaSpecificationExecutor<Contest> {

}
