package com.photography.lithuanian_press_photography.service;

import com.photography.lithuanian_press_photography.entity.Contest;
import com.photography.lithuanian_press_photography.entity.User;
import com.photography.lithuanian_press_photography.entity.UserParticipation;
import com.photography.lithuanian_press_photography.enums.ParticipationStatus;
import com.photography.lithuanian_press_photography.repository.UserParticipationRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserParticipationService {
    private final UserParticipationRepository userParticipationRepository;
    private final ContestService contestService;
    private final UserService userService;

    public Page<UserParticipation> getAllParticipation(PageRequest pageRequest, String contains) {
        return userParticipationRepository.findAll(pageRequest);
    }

    public UserParticipation createUserParticipation (UUID userId, UUID contestId){
        if (userParticipationRepository.findByUserIdAndContestId(userId, contestId).isPresent()){
            throw new EntityExistsException("Participation request exists already");
        }
        Contest contest = contestService.getContestById(contestId);
        User user = userService.getUserById(userId);
        return UserParticipation.builder()
                .status(ParticipationStatus.PENDING)
                .user(user)
                .contest(contest)
                .build();
    }

    public UserParticipation getParticipationById(UUID participationId) {
        return userParticipationRepository.findById(participationId)
                .orElseThrow(() -> new EntityNotFoundException("UserParticipation was not found with ID: " + participationId));
    }

    public void deleteParticipation(UUID participationId) {
        if (userParticipationRepository.existsById(participationId)) {
            userParticipationRepository.deleteById(participationId);
        } else {
            throw new EntityNotFoundException("UserParticipation was not found with ID: " + participationId);
        }
    }
}
