package com.photography.lithuanian_press_photography.service;

import com.photography.lithuanian_press_photography.entity.Participation;
import com.photography.lithuanian_press_photography.repository.ParticipationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipationService {
    private final ParticipationRepository participationRepository;

    public Page<Participation> getAllParticipation(PageRequest pageRequest, String contains) {
        if (contains == null) {
            return participationRepository.findAll(pageRequest);
        } else {
            return participationRepository.findAll(pageRequest);
        }
    }

    public Participation getParticipationById(UUID participationId) {
        return participationRepository.findById(participationId)
                .orElseThrow(() -> new EntityNotFoundException("Participation was not found with ID: " + participationId));
    }

    public void deleteParticipation(UUID participationId) {
        if (participationRepository.existsById(participationId)) {
            participationRepository.deleteById(participationId);
        } else {
            throw new EntityNotFoundException("Participation was not found with ID: " + participationId);
        }
    }
}
