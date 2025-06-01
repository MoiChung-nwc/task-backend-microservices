package com.chung.tasksubmission.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionResponse {
    private Long id;
    private Long taskId;
    private String content;
    private String submittedBy;
    private LocalDateTime submittedAt;
}
