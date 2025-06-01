package com.chung.authservice.repository;

import com.chung.authservice.entity.ApiPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApiPermissionRepository extends JpaRepository<ApiPermission, Long> {
    List<ApiPermission> findByServiceNameAndEnabledTrue(String serviceName);
}
