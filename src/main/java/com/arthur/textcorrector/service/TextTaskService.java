package com.arthur.textcorrector.service;

import com.arthur.textcorrector.dto.GetTaskResponse;
import com.arthur.textcorrector.entity.TaskStatus;
import com.arthur.textcorrector.entity.TextTask;
import com.arthur.textcorrector.exception.TaskNotFoundException;
import com.arthur.textcorrector.repository.TextTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TextTaskService {
    private final TextTaskRepository repository;

    public UUID createTask(String text, String language) {
        TextTask task = TextTask.builder()
                .originalText(text)
                .status(TaskStatus.NEW)
                .language(language)
                .createdAt(LocalDateTime.now())
                .build();
        repository.save(task);
        return task.getId();
    }

    public GetTaskResponse getTask(UUID id) {
        TextTask task = repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        return GetTaskResponse.builder()
                .status(task.getStatus())
                .correctedText(task.getStatus() == TaskStatus.COMPLETED ? task.getCorrectedText() : null)
                .errorMessage(task.getStatus() == TaskStatus.FAILED ? task.getErrorMessage() : null)
                .build();
    }

}
