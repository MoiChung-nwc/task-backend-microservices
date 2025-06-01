package com.chung.tasksubmission.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_submissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long taskId;       // Liên kết với task-service
    private Integer userId;    // Liên kết với user-service

    private String content;    // Nội dung nộp bài

    private LocalDateTime submittedAt;
}