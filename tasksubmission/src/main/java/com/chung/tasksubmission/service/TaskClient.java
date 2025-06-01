package com.chung.tasksubmission.service;

import com.chung.tasksubmission.config.FeignClientInterceptorConfig;
import com.chung.tasksubmission.dto.request.TaskUpdateRequest;
import com.chung.tasksubmission.dto.response.TaskResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "task-service", url = "${task-service.url}", configuration = FeignClientInterceptorConfig.class)
public interface TaskClient {
    @PutMapping("/api/tasks/{id}")
    TaskResponse updateTask(@PathVariable("id") Long taskId, @RequestBody TaskUpdateRequest request);
}
