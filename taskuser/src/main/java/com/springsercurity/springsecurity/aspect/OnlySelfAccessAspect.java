package com.springsercurity.springsecurity.aspect;

import com.springsercurity.springsecurity.annotation.OnlySelfAccess;
import com.springsercurity.springsecurity.entity.User;
import com.springsercurity.springsecurity.exception.CustomAccessDeniedException;
import com.springsercurity.springsecurity.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class OnlySelfAccessAspect {

    private final UserRepository userRepository;

    @Before("@annotation(com.springsercurity.springsecurity.annotation.OnlySelfAccess)")
    public void checkOnlySelfAccess(JoinPoint joinPoint) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            throw new CustomAccessDeniedException("Invalid Authentication");
        }
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomAccessDeniedException("User not found"));

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        OnlySelfAccess annotation = method.getAnnotation(OnlySelfAccess.class);
        String userIdParamName = annotation.userIdParam();

        String[] paramNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        Integer userIdFromParam = null;
        for (int i = 0; i < paramNames.length; i++) {
            if (paramNames[i].equals(userIdParamName)) {
                userIdFromParam = (Integer) args[i];
                break;
            }
        }

        if (userIdFromParam == null) {
            throw new IllegalArgumentException("User ID parameter not found");
        }

        var role = currentUser.getRole();

        // Nếu role là USER thì chỉ được truy cập userId của chính mình
        if (role == com.springsercurity.springsecurity.security.Role.USER
                && !currentUser.getId().equals(userIdFromParam)) {
            throw new CustomAccessDeniedException("Access Denied: cannot access other users' data");
        }
        // ADMIN và MANAGER được phép truy cập userId bất kỳ, nên không cần xử lý gì thêm
    }
}
