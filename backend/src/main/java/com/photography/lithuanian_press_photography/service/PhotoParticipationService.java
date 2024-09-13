package com.photography.lithuanian_press_photography.service;

import com.photography.lithuanian_press_photography.dto.request.contest.ContestRequest;
import com.photography.lithuanian_press_photography.dto.request.photo.PhotoParticipationRequest;
import com.photography.lithuanian_press_photography.entity.Category;
import com.photography.lithuanian_press_photography.entity.Contest;
import com.photography.lithuanian_press_photography.entity.PhotoParticipation;
import com.photography.lithuanian_press_photography.repository.PhotoParticipationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoParticipationService {
    private final PhotoParticipationRepository photoParticipationRepository;
    private final CategoryService categoryService;

    public Page<PhotoParticipation> getPhotoParticipationByCategoryId(UUID categoryId, PageRequest pageRequest) {
        return photoParticipationRepository.findByCategoryId(categoryId, pageRequest);
    }

    public PhotoParticipation getPhotosParticipationById(UUID photoParticipationId) {
        return photoParticipationRepository.findById(photoParticipationId)
                .orElseThrow(() -> new EntityNotFoundException("photo userParticipation not found with id: " + photoParticipationId));
    }

    public void deletePhotoParticipation(UUID photoParticipationId) {
        if (photoParticipationRepository.existsById(photoParticipationId)) {
            photoParticipationRepository.deleteById(photoParticipationId);
        } else {
            throw new EntityNotFoundException("photo userParticipation not found with Id: " + photoParticipationId);
        }
    }

    public PhotoParticipation createPhotoParticipation(PhotoParticipationRequest request, UUID categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        PhotoParticipation photoParticipation = PhotoParticipation.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(category)
                .uploadsLeft(category.getMaxTotalSubmissions())
                .build();
        photoParticipationRepository.save(photoParticipation);
        return photoParticipation;
    }

    public PhotoParticipation updatePhotoParticipation(PhotoParticipationRequest request, UUID photoParticipationId) {
        PhotoParticipation photoParticipation = photoParticipationRepository.findById(photoParticipationId)
                .orElseThrow(() -> new EntityNotFoundException("photo userParticipation not found"));
        photoParticipation.setName(request.getName());
        photoParticipation.setDescription(request.getDescription());
        photoParticipationRepository.save(photoParticipation);
        return photoParticipation;
    }

}
