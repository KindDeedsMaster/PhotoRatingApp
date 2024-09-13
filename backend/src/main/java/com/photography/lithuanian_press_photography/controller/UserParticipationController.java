package com.photography.lithuanian_press_photography.controller;

import com.photography.lithuanian_press_photography.entity.UserParticipation;
import com.photography.lithuanian_press_photography.service.UserParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1")
@Validated
@RequiredArgsConstructor
public class UserParticipationController {
    private final UserParticipationService userParticipationService;

    @GetMapping("/userParticipation")
    public ResponseEntity<Page<UserParticipation>> getAllParticipation(@RequestParam(defaultValue = "0") int pageNumber,
                                                                       @RequestParam(defaultValue = "25") int pageSize,
                                                                       @RequestParam(defaultValue = "createdAt") String sortBy,
                                                                       @RequestParam(defaultValue = "true") boolean sortDesc,
                                                                       @RequestParam(required = false) String contains) {
        Sort.Direction direction = sortDesc ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return ResponseEntity.ok().body(userParticipationService.getAllParticipation(pageRequest, contains));
    }

    @GetMapping(path = "{participationId}")
    public ResponseEntity<UserParticipation> getParticipation(@PathVariable UUID participationId) {
        return ResponseEntity.ok().body(userParticipationService.getParticipationById(participationId));
    }

    @DeleteMapping("/{participationId}")
    public ResponseEntity<?> deleteParticipation(@PathVariable UUID participationId) {
        userParticipationService.deleteParticipation(participationId);
        return ResponseEntity.noContent().build();
    }
}
