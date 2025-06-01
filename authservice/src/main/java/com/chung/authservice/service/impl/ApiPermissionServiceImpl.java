package com.chung.authservice.service.impl;

import com.chung.authservice.constants.MessageConstants;
import com.chung.authservice.dto.request.CheckPermissionRequest;
import com.chung.authservice.entity.ApiPermission;
import com.chung.authservice.repository.ApiPermissionRepository;
import com.chung.authservice.service.ApiPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiPermissionServiceImpl implements ApiPermissionService {

    private final ApiPermissionRepository repository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean checkPermission(CheckPermissionRequest req) {
        System.out.printf(MessageConstants.LOG_CHECKING_PERMISSION + "%n",
                req.getServiceName(), req.getApiPath(), req.getHttpMethod(), req.getRole());

        List<ApiPermission> list = repository.findByServiceNameAndEnabledTrue(req.getServiceName());

        System.out.println(MessageConstants.LOG_PERMISSIONS_FROM_DB);
        for (ApiPermission p : list) {
            System.out.println("  " + p);
        }

        // Chuẩn hóa role đầu vào, bỏ prefix "ROLE_" nếu có
        String reqRoleNormalized = req.getRole();
        if (reqRoleNormalized.startsWith(MessageConstants.ROLE_PREFIX)) {
            reqRoleNormalized = reqRoleNormalized.substring(MessageConstants.ROLE_PREFIX_LENGTH);
        }
        System.out.printf(MessageConstants.LOG_ROLE_NORMALIZED + "%n", reqRoleNormalized);

        for (ApiPermission p : list) {
            boolean methodMatches = p.getHttpMethod().equalsIgnoreCase(req.getHttpMethod());
            boolean pathMatches = pathMatcher.match(p.getApiPathPattern(), req.getApiPath());

            System.out.println(MessageConstants.LOG_PERMISSION_RECORD);
            System.out.printf(MessageConstants.LOG_DB_METHOD_MATCH + "%n", p.getHttpMethod(), methodMatches);
            System.out.printf(MessageConstants.LOG_DB_PATH_MATCH + "%n", p.getApiPathPattern(), pathMatches);

            if (methodMatches && pathMatches) {
                String[] allowedRoles = p.getRole().split(MessageConstants.ALLOWED_ROLES_SEPARATOR);
                for (String r : allowedRoles) {
                    String allowedRoleNormalized = r.trim();
                    if (allowedRoleNormalized.startsWith(MessageConstants.ROLE_PREFIX)) {
                        allowedRoleNormalized = allowedRoleNormalized.substring(MessageConstants.ROLE_PREFIX_LENGTH);
                    }
                    System.out.printf(MessageConstants.LOG_ROLE_COMPARISON + "%n",
                            allowedRoleNormalized, reqRoleNormalized);
                    if (allowedRoleNormalized.equalsIgnoreCase(reqRoleNormalized)) {
                        System.out.println(MessageConstants.LOG_PERMISSION_GRANTED);
                        return true;
                    }
                }
            }
        }

        System.out.println(MessageConstants.LOG_PERMISSION_DENIED);
        return false;
    }
}