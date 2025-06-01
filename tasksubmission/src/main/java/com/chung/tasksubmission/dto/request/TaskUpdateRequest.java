package com.chung.tasksubmission.dto.request;

import com.chung.tasksubmission.entity.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateRequest {
    private String title;
    private String description;
    private TaskStatus status;
    private String assignedTo;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
