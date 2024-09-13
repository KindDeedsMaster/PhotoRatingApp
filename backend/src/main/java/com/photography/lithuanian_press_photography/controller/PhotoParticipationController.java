package com.photography.lithuanian_press_photography.controller;

import com.photography.lithuanian_press_photography.dto.request.photo.PhotoParticipationRequest;
import com.photography.lithuanian_press_photography.entity.PhotoParticipation;
import com.photography.lithuanian_press_photography.service.PhotoParticipationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/photosParticipation")
@Validated
@RequiredArgsConstructor
public class PhotoParticipationController {
    private final PhotoParticipationService photoParticipationService;

    @GetMapping
    public ResponseEntity<Page<PhotoParticipation>> getAllPhotosParticipation(@RequestParam(defaultValue = "0") int pageNumber,
                                                                              @RequestParam(defaultValue = "25") int pageSize,
                                                                              @RequestParam(defaultValue = "createdAt") String sortBy,
                                                                              @RequestParam(defaultValue = "true") boolean sortDesc
    ) {
        Sort.Direction direction = sortDesc ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return ResponseEntity.ok().body(photoParticipationService.getAllPhotosParticipation(pageRequest));
    }

    @GetMapping(path = "/{photosParticipationId}")
    public ResponseEntity<PhotoParticipation> getPhotoParticipation(@PathVariable UUID photosParticipationId) {
        return ResponseEntity.ok().body(photoParticipationService.getPhotosParticipationById(photosParticipationId));
    }

    @PostMapping(path = "/category/{categoryId}")
    public ResponseEntity<PhotoParticipation> createPhotosParticipation(@PathVariable UUID categoryId,
                                                                        @RequestBody PhotoParticipationRequest request) {
        PhotoParticipation photoParticipation = photoParticipationService.createPhotoParticipation(request, categoryId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/photosParticipation/{photosParticipationId}")
                .buildAndExpand(photoParticipation.getId())
                .toUri();
        return ResponseEntity.created(location).body(photoParticipation);
    }

    @PutMapping("/{photosParticipationId}")
    public ResponseEntity<PhotoParticipation> updatePhotosParticipation(@PathVariable UUID photosParticipationId,
                                                                       @RequestBody @Valid PhotoParticipationRequest request) {
        return ResponseEntity
                .ok()
                .body(photoParticipationService.updatePhotoParticipation(request, photosParticipationId));
    }

    @DeleteMapping("/{photosParticipationId}")
    public ResponseEntity<?> deletePhotosParticipation(@PathVariable UUID photosParticipationId) {
        photoParticipationService.deletePhotoParticipation(photosParticipationId);
        return ResponseEntity.noContent().build();
    }
}
