package com.chung.tasksubmission.dto.response;

import com.chung.tasksubmission.constants.ApiStatus;
import com.chung.tasksubmission.constants.AppConstants;
import com.chung.tasksubmission.exception.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;     // SUCCESS | ERROR
    private int code;          // Mã HTTP hoặc mã hệ thống
    private String errorCode;  // Tên lỗi trong ErrorCode (VD: VALIDATION_ERROR)
    private String message;    // Mô tả lỗi
    private T data;            // Payload
    private String timestamp;  // Thời điểm xảy ra
    private String path;       // URI được gọi

    // Thành công
    public static <T> ApiResponse<T> success(T data, String message, String path) {
        return new ApiResponse<>(
                AppConstants.SUCCESS,
                ApiStatus.SUCCESS.getCode(),
                null,
                message,
                data,
                Instant.now().toString(),
                path
        );
    }

    // Lỗi thông thường - trả message và errorCode (dạng String)
    public static <T> ApiResponse<T> error(String message, String errorCode, String path) {
        return new ApiResponse<>(
                AppConstants.ERROR,
                ApiStatus.BAD_REQUEST.getCode(),
                errorCode,
                message,
                null,
                Instant.now().toString(),
                path
        );
    }

    // Lỗi có kèm ErrorCode enum
    public static <T> ApiResponse<T> error(T data , String message, ErrorCode errorCode, String path) {
        return new ApiResponse<>(
                AppConstants.ERROR,
                errorCode.getCode(),
                errorCode.name(),
                message,
                data,
                Instant.now().toString(),
                path
        );
    }

    // Lỗi validation (field-level)
    public static ApiResponse<Map<String, String>> error(Map<String, String> errors, String message, ErrorCode errorCode, String path) {
        return new ApiResponse<>(
                AppConstants.ERROR,
                errorCode.getCode(),
                errorCode.name(),
                message,
                errors,
                Instant.now().toString(),
                path
        );
    }

    public static <T> ApiResponse<T> forbidden(String message, String errorCode, String path) {
        return new ApiResponse<>(
                AppConstants.ERROR,
                HttpStatus.FORBIDDEN.value(),  // 403
                errorCode,
                message,
                null,
                Instant.now().toString(),
                path
        );
    }
}