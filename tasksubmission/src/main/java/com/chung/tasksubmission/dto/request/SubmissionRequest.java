package com.chung.tasksubmission.dto.request;

import com.chung.tasksubmission.entity.TaskStatus;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionRequest {
    private Long taskId;
    private String content;
    private String submittedBy;
    private TaskStatus status;
}