package com.chung.tasksubmission.exception;

public enum ErrorCode {
    SUBMISSION_NOT_FOUND(404),
    INVALID_SUBMISSION_REQUEST(400),
    TASK_UPDATE_FAILED(500),
    INTERNAL_SERVER_ERROR(500),
    VALIDATION_ERROR(400);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}