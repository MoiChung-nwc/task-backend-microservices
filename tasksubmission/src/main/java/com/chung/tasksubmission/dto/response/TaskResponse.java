package com.chung.tasksubmission.dto.response;

import com.chung.tasksubmission.entity.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private String assignedTo;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}