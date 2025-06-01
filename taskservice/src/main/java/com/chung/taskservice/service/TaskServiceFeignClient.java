package com.chung.taskservice.service;

import com.chung.taskservice.dto.request.TaskRequest;
import com.chung.taskservice.dto.response.TaskResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "task-service", url = "${task.service.url}")
public interface TaskServiceFeignClient {

    @PutMapping("/api/tasks/{id}")
    TaskResponse updateTask(
            @PathVariable("id") Long taskId,
            @RequestBody TaskRequest taskRequest
    );
}