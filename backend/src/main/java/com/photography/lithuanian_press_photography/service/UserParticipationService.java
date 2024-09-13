package com.photography.lithuanian_press_photography.service;

import com.photography.lithuanian_press_photography.entity.UserParticipation;
import com.photography.lithuanian_press_photography.repository.UserParticipationRepository;
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

    public Page<UserParticipation> getAllParticipation(PageRequest pageRequest, String contains) {
        return userParticipationRepository.findAll(pageRequest);
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
