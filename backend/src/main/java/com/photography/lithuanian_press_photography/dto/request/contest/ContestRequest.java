package com.photography.lithuanian_press_photography.dto.request.contest;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;



import java.time.ZonedDateTime;

@Getter
@Builder
@ToString
public class ContestRequest {
    @NotNull
    @NotBlank
    @Length(max = 100)
    private String name;

    @NotNull
    @NotBlank
    @Length(max = 1000, message = "CONTEST_DESCRIPTION_LENGTH_EXCEEDED")
    private String description;

    @NotNull
    @Min(value = 1, message = "AT_LEAST_ONE")
    @Max(value = 9999999, message = "TOO_MANY_SUBMISSIONS")
    private Long maxTotalSubmissions;

    @NotNull
    @Min(value = 1, message = "AT_LEAST_ONE")
    @Max(value = 9999999, message = "TOO_MANY_SUBMISSIONS")
    private Long maxUserSubmissions;

    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
}
