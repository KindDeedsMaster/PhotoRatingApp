package com.photography.lithuanian_press_photography.controller;

import com.photography.lithuanian_press_photography.auth.RegisterResponse;
import com.photography.lithuanian_press_photography.dto.request.contest.ContestRequest;
import com.photography.lithuanian_press_photography.dto.request.user.UserUpdateRequest;
import com.photography.lithuanian_press_photography.entity.Contest;
import com.photography.lithuanian_press_photography.entity.User;
import com.photography.lithuanian_press_photography.mapper.UserMapper;
import com.photography.lithuanian_press_photography.service.ContestService;
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
@RequestMapping(path = "api/v1/contests")
@Validated
@RequiredArgsConstructor
public class ContestController {
    private final ContestService contestService;

    @GetMapping
    public ResponseEntity<Page<Contest>> getAllContests(@RequestParam(defaultValue = "0") int pageNumber,
                                                        @RequestParam(defaultValue = "25") int pageSize,
                                                        @RequestParam(defaultValue = "createdAt") String sortBy,
                                                        @RequestParam(defaultValue = "true") boolean sortDesc) {
        Sort.Direction direction = sortDesc ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return ResponseEntity.ok().body(contestService.getAllContests(pageRequest));
    }

    @GetMapping(path = "{contestId}")
    public ResponseEntity<Contest> getContest(@PathVariable UUID contestId) {
        return ResponseEntity.ok().body(contestService.getContestById(contestId));
    }

    @PostMapping
    public ResponseEntity<Contest> createContest(@RequestBody ContestRequest request) {
        Contest contest = contestService.createContest(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{contestId}")
                .buildAndExpand(contest.getId())
                .toUri();
        return ResponseEntity.created(location).body(contest);
    }

    @PutMapping("/{contestId}")
    public ResponseEntity<Contest> updateContest(@PathVariable UUID contestId, @RequestBody @Valid ContestRequest contestRequest) {
        return ResponseEntity.ok().body(contestService.updateContest(contestRequest, contestId));
    }

    @DeleteMapping("/{contestId}")
    public ResponseEntity<?> deleteContest(@PathVariable UUID contestId) {
        contestService.deleteContest(contestId);
        return ResponseEntity.noContent().build();
    }
}
