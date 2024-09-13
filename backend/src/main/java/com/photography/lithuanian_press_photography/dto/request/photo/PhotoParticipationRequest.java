package com.photography.lithuanian_press_photography.dto.request.photo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
@Getter
@ToString
public class PhotoParticipationRequest {


    @NotNull
    @NotBlank
    @Length(max = 100, message = "PHOTO_PARTICIPATION_NAME_LENGTH_EXCEEDED")
    private String name;

    @NotNull
    @NotBlank
    @Length(max = 1000, message = "PHOTO_PARTICIPATION_DESCRIPTION_LENGTH_EXCEEDED")
    private String description;


}
