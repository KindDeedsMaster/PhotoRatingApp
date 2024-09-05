package com.photography.lithuanian_press_photography.service;

import com.photography.lithuanian_press_photography.dto.request.contest.ContestRequest;
import com.photography.lithuanian_press_photography.entity.Contest;
import com.photography.lithuanian_press_photography.repository.ContestRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContestService {
    private final ContestRepository contestRepository;

    public Page<Contest> getAllContests(PageRequest pageRequest) {
        return contestRepository.findAll(pageRequest);
    }

    public Contest getContestById(UUID contestId) {
        return contestRepository.findById(contestId)
                .orElseThrow(() -> new EntityNotFoundException("contest not found with Id: " + contestId));
    }

    public void deleteContest(UUID contestId) {
        if (contestRepository.existsById(contestId)) {
            contestRepository.deleteById(contestId);
        } else {
            throw new EntityNotFoundException("contest not found with Id: " + contestId);
        }
    }

    public Contest createContest(ContestRequest request) {
        Contest contest = Contest.builder()
                .name(request.getName())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .maxUserSubmissions(request.getMaxUserSubmissions())
                .maxTotalSubmissions(request.getMaxTotalSubmissions())
                .build();
        contestRepository.save(contest);
        return contest;
    }

    public Contest updateContest(ContestRequest request, UUID contestId) {
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new EntityNotFoundException("contest not found"));
        contest.setName(request.getName());
        contest.setDescription(request.getDescription());
        contest.setEndDate(request.getEndDate());
        contest.setStartDate(request.getStartDate());
        contest.setMaxTotalSubmissions(request.getMaxTotalSubmissions());
        contest.setMaxUserSubmissions(request.getMaxUserSubmissions());
        contestRepository.save(contest);
        return contest;
    }


}
