package com.chung.tasksubmission.service.impl;

import com.chung.tasksubmission.dto.request.SubmissionRequest;
import com.chung.tasksubmission.dto.request.TaskUpdateRequest;
import com.chung.tasksubmission.dto.response.SubmissionResponse;
import com.chung.tasksubmission.entity.TaskStatus;
import com.chung.tasksubmission.entity.Submission;
import com.chung.tasksubmission.repository.TaskSubmissionRepository;
import com.chung.tasksubmission.service.SubmissionService;
import com.chung.tasksubmission.service.TaskClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final TaskSubmissionRepository submissionRepository;
    private final TaskClient taskClient;

    @Override
    public SubmissionResponse submitTask(SubmissionRequest request) {
        Submission submission = Submission.builder()
                .taskId(request.getTaskId())
                .userId(Integer.parseInt(request.getSubmittedBy())) // map từ String userId sang int
                .content(request.getContent())
                .submittedAt(LocalDateTime.now())
                .build();

        Submission saved = submissionRepository.save(submission);

        // Cập nhật trạng thái task
        TaskUpdateRequest updateRequest = TaskUpdateRequest.builder()
                .status(request.getStatus())  // lấy trạng thái từ client
                .build();
        taskClient.updateTask(request.getTaskId(), updateRequest);

        return SubmissionResponse.builder()
                .id(saved.getId())
                .taskId(saved.getTaskId())
                .content(saved.getContent())
                .submittedBy(String.valueOf(saved.getUserId())) // map int sang String
                .submittedAt(saved.getSubmittedAt())
                .build();
    }
}