package com.chung.tasksubmission.service;

import com.chung.tasksubmission.dto.request.SubmissionRequest;
import com.chung.tasksubmission.dto.response.SubmissionResponse;

public interface SubmissionService {
    SubmissionResponse submitTask(SubmissionRequest request);
}
