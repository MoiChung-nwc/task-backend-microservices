package com.chung.authservice.service;

import com.chung.authservice.dto.request.CheckPermissionRequest;

public interface ApiPermissionService {
    boolean checkPermission(CheckPermissionRequest request);
}
