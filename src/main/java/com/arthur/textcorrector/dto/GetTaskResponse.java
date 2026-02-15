package com.arthur.textcorrector.dto;

import com.arthur.textcorrector.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetTaskResponse {
    private TaskStatus status;
    private String correctedText;
    private String errorMessage;
}