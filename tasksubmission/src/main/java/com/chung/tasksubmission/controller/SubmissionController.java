package com.chung.tasksubmission.controller;

import com.chung.tasksubmission.dto.request.SubmissionRequest;
import com.chung.tasksubmission.dto.response.SubmissionResponse;
import com.chung.tasksubmission.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<SubmissionResponse> submitTask(@Valid @RequestBody SubmissionRequest request) {
        SubmissionResponse response = submissionService.submitTask(request);
        return ResponseEntity.ok(response);
    }
}