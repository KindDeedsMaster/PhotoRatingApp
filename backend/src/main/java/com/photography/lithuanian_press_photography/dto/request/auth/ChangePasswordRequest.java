package com.photography.lithuanian_press_photography.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ChangePasswordRequest {
    @NotBlank
    private String newPassword;
    @NotBlank
    private String oldPassword;
}
