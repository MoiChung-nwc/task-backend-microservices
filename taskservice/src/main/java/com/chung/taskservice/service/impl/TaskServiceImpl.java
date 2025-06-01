package com.chung.taskservice.service.impl;

import com.chung.taskservice.constants.TaskConstants;
import com.chung.taskservice.dto.request.TaskRequest;
import com.chung.taskservice.dto.response.TaskResponse;
import com.chung.taskservice.entity.Task;
import com.chung.taskservice.exception.ResourceNotFoundException;
import com.chung.taskservice.repository.TaskRepository;
import com.chung.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public TaskResponse createTask(TaskRequest request) {
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .assignedTo(request.getAssignedTo())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();

        Task saved = taskRepository.save(task);
        return mapToResponse(saved);
    }

    @Override
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TaskConstants.TASK_NOT_FOUND + id));

        // Chỉ cập nhật status
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        Task updated = taskRepository.save(task);
        return mapToResponse(updated);
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TaskConstants.TASK_NOT_FOUND + id));
        return mapToResponse(task);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TaskConstants.TASK_NOT_FOUND + id));
        taskRepository.delete(task);
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .assignedTo(task.getAssignedTo())
                .startTime(task.getStartTime())
                .endTime(task.getEndTime())
                .build();
    }
}