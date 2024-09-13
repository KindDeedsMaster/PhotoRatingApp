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
@RequestMapping(path = "api/v1")
@Validated
@RequiredArgsConstructor
public class PhotoParticipationController {
    private final PhotoParticipationService photoParticipationService;

    @GetMapping(path = "/category/{categoryId}/photosParticipation")
    public ResponseEntity<Page<PhotoParticipation>> getAllPhotosParticipation(@PathVariable UUID categoryId,
                                                                              @RequestParam(defaultValue = "0") int pageNumber,
                                                                              @RequestParam(defaultValue = "25") int pageSize,
                                                                              @RequestParam(defaultValue = "createdAt") String sortBy,
                                                                              @RequestParam(defaultValue = "true") boolean sortDesc) {
        Sort.Direction direction = sortDesc ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return ResponseEntity.ok().body(photoParticipationService.getPhotoParticipationByCategoryId(categoryId, pageRequest));
    }

    @GetMapping(path = "/photosParticipation/{id}")
    public ResponseEntity<PhotoParticipation> getPhotoParticipation(@PathVariable UUID id) {
        return ResponseEntity.ok().body(photoParticipationService.getPhotosParticipationById(id));
    }

    @PostMapping(path = "/category/{categoryId}/photosParticipation")
    public ResponseEntity<PhotoParticipation> createPhotosParticipation(@PathVariable UUID categoryId,
                                                                        @RequestBody PhotoParticipationRequest request) {
        PhotoParticipation photoParticipation = photoParticipationService.createPhotoParticipation(request, categoryId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(photoParticipation.getId())
                .toUri();
        return ResponseEntity.created(location).body(photoParticipation);
    }

    @PutMapping("/photosParticipation/{id}")
    public ResponseEntity<PhotoParticipation> updatePhotosParticipation(@PathVariable UUID id,
                                                                        @RequestBody @Valid PhotoParticipationRequest request) {
        return ResponseEntity.ok().body(photoParticipationService.updatePhotoParticipation(request, id));
    }

    @DeleteMapping("/photosParticipation/{id}")
    public ResponseEntity<?> deletePhotosParticipation(@PathVariable UUID id) {
        photoParticipationService.deletePhotoParticipation(id);
        return ResponseEntity.noContent().build();
    }

}
