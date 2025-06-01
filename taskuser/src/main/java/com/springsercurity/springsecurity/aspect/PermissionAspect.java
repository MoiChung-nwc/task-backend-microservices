package com.springsercurity.springsecurity.aspect;

import com.springsercurity.springsecurity.security.PermissionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {

    private final PermissionService permissionService;
    private final HttpServletRequest request;

    @Pointcut("@annotation(com.springsercurity.springsecurity.annotation.CheckPermission)")
    public void checkPermissionPointcut() {
        // Đây là pointcut — không cần code
    }

    @Before("checkPermissionPointcut()")
    public void beforeCheckPermission() {
        permissionService.checkPermission(request);
    }
}
