package com.photography.lithuanian_press_photography.dto.request.categoty;

import com.photography.lithuanian_press_photography.enums.PhotoSubmissionType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.*;

import java.util.List;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class CategoryDTO {

    private UUID id;

    @NotBlank
    @Length(max = 100, message = "CATEGORY_NAME_LENGTH_EXCEEDED")
    private String name;

    @Length(max = 1000, message = "CATEGORY_DESCRIPTION_LENGTH_EXCEEDED")
    private String description;

    @Min(value = 1, message = "AT_LEAST_ONE")
    @Max(value = 9999999, message = "TOO_MANY_SUBMISSIONS")
    private long maxTotalSubmissions;

    @Min(value = 1, message = "AT_LEAST_ONE")
    @Max(value = 9999999, message = "TOO_MANY_SUBMISSIONS")
    private long maxUserSubmissions;

    @NotNull
    private PhotoSubmissionType type;

    private List<String> uploadedPhotos;
}
