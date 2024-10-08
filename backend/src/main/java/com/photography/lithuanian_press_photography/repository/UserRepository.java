package com.photography.lithuanian_press_photography.repository;



import com.photography.lithuanian_press_photography.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail (String email);
    Page<User> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);
}
