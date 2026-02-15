package com.arthur.textcorrector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateTaskResponse {
    private UUID id;
}
