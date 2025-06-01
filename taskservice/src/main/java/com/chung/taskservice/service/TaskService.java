package com.chung.taskservice.service;

import com.chung.taskservice.dto.request.TaskRequest;
import com.chung.taskservice.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskRequest request);

    TaskResponse updateTask(Long id, TaskRequest request);

    TaskResponse getTaskById(Long id);

    void deleteTask(Long id);

    List<TaskResponse> getAllTasks();
}