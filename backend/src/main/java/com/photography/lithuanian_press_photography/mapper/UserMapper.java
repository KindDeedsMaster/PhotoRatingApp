package com.photography.lithuanian_press_photography.mapper;

import com.photography.lithuanian_press_photography.dto.request.user.UserUpdateRequest;
import com.photography.lithuanian_press_photography.dto.response.ParticipatingUser;
import com.photography.lithuanian_press_photography.entity.User;

public class UserMapper {

    private UserMapper(){}

    public static User mapToUser(UserUpdateRequest updateDTO) {
        return User.builder()
                .firstName(updateDTO.getFirstName())
                .lastName(updateDTO.getLastName())
                .email(updateDTO.getEmail())
                .institution(updateDTO.getInstitution())
                .isFreelance(updateDTO.getInstitution() == null || updateDTO.getInstitution().isBlank())
                .birthYear(updateDTO.getBirthYear())
                .maxTotal(updateDTO.getMaxTotal())
                .maxSinglePhotos(updateDTO.getMaxSinglePhotos())
                .maxCollections(updateDTO.getMaxCollections())
                .phoneNumber(updateDTO.getPhoneNumber())
                .build();
    }

        public static ParticipatingUser userToParticipatingUsr(User user) {
        return ParticipatingUser.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
