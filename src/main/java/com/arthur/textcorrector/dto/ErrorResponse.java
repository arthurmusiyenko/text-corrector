package com.arthur.textcorrector.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private String errorMessage;
    private int errorCode;
    private LocalDateTime timestamp;
    private String path;
}
