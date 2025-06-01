package com.springsercurity.springsecurity.exception;

import com.springsercurity.springsecurity.constants.AppConstants;
import com.springsercurity.springsecurity.constants.MessageConstants;
import com.springsercurity.springsecurity.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.springsercurity.springsecurity.exception.ErrorCode.*;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Method tiện ích dùng chung cho tất cả lỗi
    private <T> ResponseEntity<ApiResponse<T>> buildErrorResponse(T data, ErrorCode errorCode, String path) {
        return ResponseEntity.status(errorCode.getCode())
                .body(ApiResponse.error(data, errorCode.getMessage(), errorCode, path));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        log.warn("Validation failed at {}: {}", request.getRequestURI(), ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return buildErrorResponse(errors, VALIDATION_ERROR, request.getRequestURI());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmailAlreadyExists(
            EmailAlreadyExistsException ex, HttpServletRequest request) {

        log.warn("EmailAlreadyExistsException at {}: {}", request.getRequestURI(), ex.getMessage());

        return buildErrorResponse(null, EMAIL_EXISTED, request.getRequestURI());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleUsernameNotFoundException(
            UsernameNotFoundException ex, HttpServletRequest request) {

        log.warn("UsernameNotFoundException at {}: {}", request.getRequestURI(), ex.getMessage());

        return buildErrorResponse(ex.getMessage(), USER_NOT_FOUND, request.getRequestURI());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<String>> handleBadCredentialsException(
            BadCredentialsException ex, HttpServletRequest request) {

        log.warn("BadCredentialsException at {}: {}", request.getRequestURI(), ex.getMessage());

        // Trả về response với code 401 hoặc 400 tùy bạn muốn
        return buildErrorResponse(
                MessageConstants.AUTHENTICATION_FAILED,  // message trả về client
                ErrorCode.UNAUTHORIZED,  // bạn cần định nghĩa ErrorCode này
                request.getRequestURI()
        );
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidPasswordException(
            InvalidPasswordException ex, HttpServletRequest request) {
        log.warn("InvalidPasswordException at {}: {}", request.getRequestURI(), ex.getMessage());

        return buildErrorResponse(ex.getMessage(), VALIDATION_ERROR, request.getRequestURI());
    }

    @ExceptionHandler({AccessDeniedException.class, CustomAccessDeniedException.class, AuthorizationDeniedException.class})
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(Exception ex, HttpServletRequest request) {
        log.warn("AccessDeniedException at {}: {}", request.getRequestURI(), ex.getMessage());

        ApiResponse<?> response = ApiResponse.forbidden(
                ex.getMessage(),
                ErrorCode.FORBIDDEN.name(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(
            Exception ex, HttpServletRequest request) {

        log.error("Unhandled exception at {}: {}", request.getRequestURI(), ex.getMessage(), ex);

        return buildErrorResponse(MessageConstants.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR, request.getRequestURI());
    }
}
