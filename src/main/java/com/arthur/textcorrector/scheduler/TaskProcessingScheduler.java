package com.arthur.textcorrector.scheduler;

import com.arthur.textcorrector.client.YandexClient;
import com.arthur.textcorrector.entity.TaskStatus;
import com.arthur.textcorrector.entity.TextTask;
import com.arthur.textcorrector.repository.TextTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskProcessingScheduler {
    private final TextTaskRepository taskRepository;
    private final YandexClient yandexSpellerClient;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void processTasks() {
        List<TextTask> tasks = taskRepository.findTop10ByStatus(TaskStatus.NEW);
        if (tasks.isEmpty()) {
            return;
        }
        for (TextTask task : tasks) {
            try {
                log.info("Processing task {}", task.getId());
                task.setStatus(TaskStatus.PROCESSING);
                String corrected = yandexSpellerClient.correctText(
                        task.getOriginalText(),
                        task.getLanguage()
                );
                task.setCorrectedText(corrected);
                task.setStatus(TaskStatus.COMPLETED);
            } catch (Exception e) {
                log.error("Task {} failed", task.getId(), e);
                task.setStatus(TaskStatus.FAILED);
                task.setErrorMessage(e.getMessage());
            }
        }

    }
}
