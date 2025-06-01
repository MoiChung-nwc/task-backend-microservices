package com.chung.taskservice.dto.response;

import com.chung.taskservice.entity.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private String assignedTo;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}