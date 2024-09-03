package com.photography.lithuanian_press_photography.service;

import com.photography.lithuanian_press_photography.auth.AuthenticationService;
import com.photography.lithuanian_press_photography.entity.User;
import com.photography.lithuanian_press_photography.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user not found with ID: " + userId));
    }

}
