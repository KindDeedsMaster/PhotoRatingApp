package com.photography.lithuanian_press_photography.service;

import com.photography.lithuanian_press_photography.entity.Participation;
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

    public Page<Participation> getAllParticipation(PageRequest pageRequest, String contains) {
        if (contains == null) {
            return userParticipationRepository.findAll(pageRequest);
        } else {
            return userParticipationRepository.findAll(pageRequest);
        }
    }

    public Participation getParticipationById(UUID participationId) {
        return userParticipationRepository.findById(participationId)
                .orElseThrow(() -> new EntityNotFoundException("Participation was not found with ID: " + participationId));
    }

    public void deleteParticipation(UUID participationId) {
        if (userParticipationRepository.existsById(participationId)) {
            userParticipationRepository.deleteById(participationId);
        } else {
            throw new EntityNotFoundException("Participation was not found with ID: " + participationId);
        }
    }
}
