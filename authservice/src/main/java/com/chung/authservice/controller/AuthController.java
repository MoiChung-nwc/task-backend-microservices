package com.chung.authservice.controller;

import com.chung.authservice.dto.request.CheckPermissionRequest;
import com.chung.authservice.service.ApiPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ApiPermissionService service;

    @PostMapping("/check-permission")
    public boolean checkPermission(@RequestBody CheckPermissionRequest request) {
        return service.checkPermission(request);
    }
}