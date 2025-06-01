package com.chung.tasksubmission.repository;

import com.chung.tasksubmission.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskSubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByTaskId(Long taskId);
    List<Submission> findByUserId(Integer userId);
}
