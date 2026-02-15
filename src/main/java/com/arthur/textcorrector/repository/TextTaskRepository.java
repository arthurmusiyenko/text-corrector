package com.arthur.textcorrector.repository;

import com.arthur.textcorrector.entity.TaskStatus;
import com.arthur.textcorrector.entity.TextTask;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TextTaskRepository extends JpaRepository<TextTask, UUID> {

    List<TextTask> findAllByStatus(TaskStatus status, Pageable pageable);

    List<TextTask> findTop10ByStatus(TaskStatus status);
}
