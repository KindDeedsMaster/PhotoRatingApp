package com.photography.lithuanian_press_photography.repository;

import com.photography.lithuanian_press_photography.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserParticipationRepository extends JpaRepository <Participation, UUID> {
   // Page<Participation> findByUserLastNameContainingIgnoreCase(PageRequest pageRequest, String contains);
}
