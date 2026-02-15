package com.arthur.textcorrector.scheduler;

import com.arthur.textcorrector.entity.TaskStatus;
import com.arthur.textcorrector.entity.TextTask;
import com.arthur.textcorrector.repository.TextTaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class SchedulerIntegrationTest {
    @Autowired
    private TextTaskRepository repository;
    @Autowired
    private TaskProcessingScheduler scheduler;

    @Test
    void shouldProcessTask() throws InterruptedException {
        TextTask task = new TextTask();
        task.setOriginalText("Карова дает малако");
        task.setLanguage("ru");
        task.setStatus(TaskStatus.NEW);
        task.setCreatedAt(LocalDateTime.now());
        repository.save(task);
        scheduler.processTasks();
        TextTask updated = repository.findById(task.getId()).orElseThrow();
        assertEquals(TaskStatus.COMPLETED, updated.getStatus());
        assertEquals("Корова дает молоко", updated.getCorrectedText());
    }
}
