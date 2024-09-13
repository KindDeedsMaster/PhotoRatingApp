package com.photography.lithuanian_press_photography.repository;

import com.photography.lithuanian_press_photography.entity.PhotoParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PhotoParticipationRepository extends JpaRepository<PhotoParticipation, UUID> {
}
