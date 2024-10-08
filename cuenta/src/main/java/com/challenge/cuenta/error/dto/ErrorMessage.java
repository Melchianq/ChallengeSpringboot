package com.challenge.cuenta.error.dto;

import lombok.*;
import org.springframework.http.HttpStatus;


@Data
@NoArgsConstructor
public class ErrorMessage {
    private HttpStatus status;
    private String message;

    public ErrorMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
