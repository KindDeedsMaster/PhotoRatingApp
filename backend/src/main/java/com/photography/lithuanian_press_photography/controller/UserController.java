package com.photography.lithuanian_press_photography.controller;

import com.photography.lithuanian_press_photography.dto.request.user.UserUpdateRequest;
import com.photography.lithuanian_press_photography.entity.User;
import com.photography.lithuanian_press_photography.mapper.UserMapper;
import com.photography.lithuanian_press_photography.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable UUID userId) {
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(@RequestParam(defaultValue = "0") @Min(0) Integer page,
                                                  @RequestParam(defaultValue = "25") @Min(0) Integer limit,
                                                  @RequestParam(defaultValue = "lastName") String sortBy,
                                                  @RequestParam(defaultValue = "false") boolean sortDesc,
                                                  @RequestParam(required = false) String contains
    ) {
        Sort.Direction direction = sortDesc ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        PageRequest pageRequest = PageRequest.of(page, limit, sort);
        return ResponseEntity.ok().body(userService.getUsers(pageRequest, contains));
    }
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable UUID userId, @RequestBody @Valid UserUpdateRequest updateDTO) {
        return ResponseEntity.ok().body(userService.updateUser(UserMapper.mapToUser(updateDTO), userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }



}
