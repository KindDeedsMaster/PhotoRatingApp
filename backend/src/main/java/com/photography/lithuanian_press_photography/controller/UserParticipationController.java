package com.photography.lithuanian_press_photography.controller;

import com.photography.lithuanian_press_photography.dto.request.categoty.CategoryDTO;
import com.photography.lithuanian_press_photography.entity.Category;
import com.photography.lithuanian_press_photography.entity.User;
import com.photography.lithuanian_press_photography.entity.UserParticipation;
import com.photography.lithuanian_press_photography.service.UserParticipationService;
import com.photography.lithuanian_press_photography.service.UserService;
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
public class UserParticipationController {
    private final UserParticipationService userParticipationService;
    private final UserService userService;

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

    // TODO: 14/09/2024 change @RequestBody to @AuthenticationPrincipal
    @PostMapping("/contests/{contestId}/userParticipation")
    public ResponseEntity<UserParticipation> createUserParticipation(@PathVariable UUID contestId,
                                                                     @RequestBody User user) {
        UserParticipation userParticipation = userParticipationService.createUserParticipation(contestId, user.getId());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).body(userParticipation);
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
