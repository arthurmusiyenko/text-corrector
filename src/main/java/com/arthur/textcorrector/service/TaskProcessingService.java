package com.arthur.textcorrector.service;


import com.arthur.textcorrector.entity.TaskStatus;
import com.arthur.textcorrector.entity.TextTask;
import com.arthur.textcorrector.repository.TextTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskProcessingService {
    private final TextTaskRepository repository;

    public void processPendingTasks() {
        List<TextTask> tasks = repository.findAllByStatus(TaskStatus.NEW, PageRequest.of(0, 10));
        for (TextTask task : tasks) {
            processTask();
        }
    }

    private void processTask() {

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStatus(TextTask task, TaskStatus status) {
        task.setStatus(status);
        repository.save(task);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void completeTask(TextTask task, String result) {
        task.setCorrectedText(result);
        task.setStatus(TaskStatus.COMPLETED);
        repository.save(task);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void failTask(TextTask task, String errorMsg) {
        task.setErrorMessage(errorMsg);
        task.setStatus(TaskStatus.FAILED);
        repository.save(task);
    }
}
