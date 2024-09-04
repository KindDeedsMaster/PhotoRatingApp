package com.photography.lithuanian_press_photography.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordOnDemandResponse {
    private HttpStatus httpStatus;
    private String message;
}
