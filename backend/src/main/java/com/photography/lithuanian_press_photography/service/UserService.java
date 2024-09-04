package com.photography.lithuanian_press_photography.service;

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

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user not found with Id: " + userId));
    }

    public Page<User> getUsers(PageRequest pageRequest, String lastName) {
        if (lastName == null) {
            return userRepository.findAll(pageRequest);
        } else {
            return userRepository.findByLastNameContainingIgnoreCase(lastName, pageRequest);
        }
    }

    public User updateUser(User updatedUser, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with Id: " + userId));
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setBirthYear(updatedUser.getBirthYear());
        user.setIsFreelance(updatedUser.getIsFreelance());
        user.setInstitution(updatedUser.getInstitution());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        return userRepository.save(user);
    }

    public void deleteUser(UUID userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new EntityNotFoundException("User not found with Id: " + userId);
        }
    }


}
