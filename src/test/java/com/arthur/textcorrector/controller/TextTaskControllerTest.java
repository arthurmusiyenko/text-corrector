package com.arthur.textcorrector.controller;

import com.arthur.textcorrector.dto.CreateTaskRequest;
import com.arthur.textcorrector.exception.GlobalExceptionHandler;
import com.arthur.textcorrector.exception.TaskNotFoundException;
import com.arthur.textcorrector.service.TextTaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({TextTaskController.class, GlobalExceptionHandler.class})
class TextTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TextTaskService service;

    @Test
    void shouldCreateTaskSuccessfully() throws Exception {
        UUID taskId = UUID.randomUUID();
        CreateTaskRequest request = new CreateTaskRequest("Текст с ошыбкой", "RU");

        when(service.createTask(anyString(), anyString())).thenReturn(taskId);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.toString()));
    }

    @Test
    void shouldReturn400WhenRequestIsInvalid() throws Exception {
        CreateTaskRequest invalidRequest = new CreateTaskRequest("", "ru");

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn404WhenTaskNotFound() throws Exception {
        UUID taskId = UUID.randomUUID();
        when(service.getTask(taskId)).thenThrow(new TaskNotFoundException(taskId));

        mockMvc.perform(get("/tasks/{id}", taskId))
                .andExpect(status().isNotFound());
    }
}