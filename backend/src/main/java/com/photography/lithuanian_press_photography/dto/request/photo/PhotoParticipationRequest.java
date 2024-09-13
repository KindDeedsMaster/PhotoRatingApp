package com.photography.lithuanian_press_photography.dto.request.photo;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
@Getter
@ToString
public class PhotoParticipationRequest {


    @NonNull
    @NotBlank
    @Length(max = 100, message = "CONTEST_NAME_LENGTH_EXCEEDED")
    private String name;

    @NonNull
    @NotBlank
    @Length(max = 1000, message = "CONTEST_DESCRIPTION_LENGTH_EXCEEDED")
    private String description;


}
