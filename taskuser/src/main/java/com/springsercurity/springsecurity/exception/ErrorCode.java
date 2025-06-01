package com.springsercurity.springsecurity.exception;

import com.springsercurity.springsecurity.constants.ApiStatus;
import com.springsercurity.springsecurity.constants.MessageConstants;

/**
 * Enum chứa các mã lỗi để dễ dàng quản lý các mã lỗi trả về khi có sự cố xảy ra trong hệ thống.
 */
public enum ErrorCode {

    // User
    EMAIL_EXISTED(ApiStatus.BAD_REQUEST.getCode(), MessageConstants.EMAIL_EXISTED),
    USER_NOT_FOUND(ApiStatus.NOT_FOUND.getCode(), MessageConstants.USER_NOT_FOUND_WITH_EMAIL),
    UNAUTHORIZED(ApiStatus.UNAUTHORIZED.getCode(), MessageConstants.AUTHENTICATION_FAILED),
    FORBIDDEN(ApiStatus.FORBIDDEN.getCode(), "Forbidden action"),

    // Schedule
    SCHEDULE_NOT_FOUND(ApiStatus.NOT_FOUND.getCode(), MessageConstants.SCHEDULE_NOT_FOUND),
    DUPLICATE_SCHEDULE(ApiStatus.BAD_REQUEST.getCode(), MessageConstants.DUPLICATE_SCHEDULE),
    INVALID_ID_ON_CREATE(ApiStatus.BAD_REQUEST.getCode(), MessageConstants.ID_MUST_BE_NULL_ON_CREATE),
    INVALID_ID_ON_UPDATE(ApiStatus.BAD_REQUEST.getCode(), MessageConstants.ID_MUST_BE_PROVIDED_ON_UPDATE),
    INVALID_DATE_RANGE(ApiStatus.BAD_REQUEST.getCode(), MessageConstants.START_DATE_BEFORE_END_DATE),

    // Validation
    VALIDATION_ERROR(ApiStatus.VALIDATION_ERROR.getCode(), MessageConstants.VALIDATION_FAILED),
    FIELD_VALIDATION_ERROR(ApiStatus.VALIDATION_ERROR.getCode(), MessageConstants.VALIDATION_FAILED),

    // Common
    NOT_FOUND(ApiStatus.NOT_FOUND.getCode(), MessageConstants.NOT_FOUND),
    INTERNAL_SERVER_ERROR(ApiStatus.INTERNAL_ERROR.getCode(), MessageConstants.INTERNAL_SERVER_ERROR),
    ACCESS_DENIED(ApiStatus.FORBIDDEN.getCode(), MessageConstants.ACCESS_DENIED);


    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
