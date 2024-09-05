package com.photography.lithuanian_press_photography.controller;

import com.photography.lithuanian_press_photography.dto.request.contest.ContestRequest;
import com.photography.lithuanian_press_photography.entity.Contest;
import com.photography.lithuanian_press_photography.entity.Participation;
import com.photography.lithuanian_press_photography.service.ParticipationService;
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
@RequestMapping(path = "api/v1/participation")
@Validated
@RequiredArgsConstructor
public class ParticipationController {
    private final ParticipationService participationService;

    @GetMapping
    public ResponseEntity<Page<Participation>> getAllParticipation(@RequestParam(defaultValue = "0") int pageNumber,
                                                                    @RequestParam(defaultValue = "25") int pageSize,
                                                                    @RequestParam(defaultValue = "createdAt") String sortBy,
                                                                    @RequestParam(defaultValue = "true") boolean sortDesc,
                                                                    @RequestParam(required = false) String contains
    ) {
        Sort.Direction direction = sortDesc ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return ResponseEntity.ok().body(participationService.getAllParticipation(pageRequest, contains));
    }

    @GetMapping(path = "{participationId}")
    public ResponseEntity<Participation> getParticipation(@PathVariable UUID participationId) {
        return ResponseEntity.ok().body(participationService.getParticipationById(participationId));
    }

    @DeleteMapping("/{participationId}")
    public ResponseEntity<?> deleteParticipation(@PathVariable UUID participationId) {
        participationService.deleteParticipation(participationId);
        return ResponseEntity.noContent().build();
    }
}
