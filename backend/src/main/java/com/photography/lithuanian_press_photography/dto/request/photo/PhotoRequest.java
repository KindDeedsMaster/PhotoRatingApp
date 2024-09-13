package com.photography.lithuanian_press_photography.dto.request.photo;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@ToString
public class PhotoRequest {

    @NonNull
    @NotBlank
    @Length(max = 100, message = "PHOTO_NAME_LENGTH_EXCEEDED")
    private String name;

    @NonNull
    @NotBlank
    @Length(max = 1000, message = "PHOTO_DESCRIPTION_LENGTH_EXCEEDED")
    private String description;

    private UUID categoryId;

    private UUID userId;

    private MultipartFile[] files;

}
