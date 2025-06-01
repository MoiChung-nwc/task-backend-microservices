package com.springsercurity.springsecurity.security;

import com.springsercurity.springsecurity.entity.User;
import com.springsercurity.springsecurity.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final UserRepository userRepository;

    public void checkPermission(HttpServletRequest request) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authentication");
        }

        String email = authentication.getName();
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }

        Role role = userOpt.get().getRole();
        String method = request.getMethod();
        String uri = request.getRequestURI();

        AntPathMatcher matcher = new AntPathMatcher();
        Set<Permission> permissions = role.getPermissions();

        boolean granted = permissions.stream().anyMatch(permission ->
                permission.getMethod().equalsIgnoreCase(method)
                        && matcher.match(permission.getPath(), uri)
        );

        if (!granted) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
    }
}