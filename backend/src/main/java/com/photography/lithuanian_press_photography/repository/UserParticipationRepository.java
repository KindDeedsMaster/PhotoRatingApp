package com.photography.lithuanian_press_photography.repository;

import com.photography.lithuanian_press_photography.entity.UserParticipation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserParticipationRepository extends JpaRepository<UserParticipation, UUID> {
    Page<UserParticipation> findByContestId(UUID contestId, PageRequest pageRequest);

    Optional<UserParticipation> findByUserIdAndContestId(UUID userId, UUID contestID);

}
