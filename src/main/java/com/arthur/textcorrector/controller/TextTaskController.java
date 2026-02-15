package com.arthur.textcorrector.controller;


import com.arthur.textcorrector.dto.CreateTaskRequest;
import com.arthur.textcorrector.dto.CreateTaskResponse;
import com.arthur.textcorrector.dto.GetTaskResponse;
import com.arthur.textcorrector.service.TextTaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TextTaskController {

    private final TextTaskService service;

    @PostMapping
    public ResponseEntity<CreateTaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        UUID id = service.createTask(request.getText(), request.getLanguage());
        return ResponseEntity.ok(new CreateTaskResponse(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTaskResponse> getTask(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getTask(id));
    }
}
