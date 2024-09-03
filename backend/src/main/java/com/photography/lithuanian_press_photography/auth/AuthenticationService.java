package com.photography.lithuanian_press_photography.auth;

import com.photography.lithuanian_press_photography.config.JwtService;
import com.photography.lithuanian_press_photography.entity.User;
import com.photography.lithuanian_press_photography.enums.Role;
import com.photography.lithuanian_press_photography.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${app.constants.user-defaults.max-photos.total}")
    private int defaultMaxTotal;
    @Value("${app.constants.user-defaults.max-photos.single}")
    private int defaultMaxSinglePhotos;
    @Value("${app.constants.user-defaults.max-photos.collection}")
    private int defaultMaxCollections;

    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EntityExistsException(String.format("email \"%s\" already in use", request.getEmail()));
        }
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .birthYear(request.getBirthYear())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .customLimits(false)
                .maxTotal(defaultMaxTotal)
                .maxSinglePhotos(defaultMaxSinglePhotos)
                .maxCollections(defaultMaxCollections)
                .isNonLocked(true)
                .isEnabled(true)
                .build();
        userRepository.save(user);
        return RegisterResponse
                .builder()
                .user(user)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(String.format("user with email \"%s\" not found", request.getEmail())));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword())
        );
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(user)
                .build();
    }
}
