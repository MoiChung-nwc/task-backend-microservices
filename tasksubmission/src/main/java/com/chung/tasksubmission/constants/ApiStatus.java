package com.chung.tasksubmission.constants;

/**
 * Enum đại diện cho các trạng thái phản hồi API.
 */
public enum ApiStatus {
    SUCCESS(200),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    VALIDATION_ERROR(422),
    INTERNAL_ERROR(500);


    private final int code;

    ApiStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
