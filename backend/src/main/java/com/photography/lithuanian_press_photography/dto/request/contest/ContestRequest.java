package com.photography.lithuanian_press_photography.dto.request.contest;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.ZonedDateTime;

@Getter
@Builder
@ToString
public class ContestRequest {
    @NonNull
    @NotBlank
    @Length(max = 100, message = "CONTEST_NAME_LENGTH_EXCEEDED")
    private String name;

    @NonNull
    @NotBlank
    @Length(max = 1000, message = "CONTEST_DESCRIPTION_LENGTH_EXCEEDED")
    private String description;

    @NonNull
    @Min(value = 1, message = "AT_LEAST_ONE")
    @Max(value = 9999999, message = "TOO_MANY_SUBMISSIONS")
    private Long maxTotalSubmissions;

    @NonNull
    @Min(value = 1, message = "AT_LEAST_ONE")
    @Max(value = 9999999, message = "TOO_MANY_SUBMISSIONS")
    private Long maxUserSubmissions;

    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
}
