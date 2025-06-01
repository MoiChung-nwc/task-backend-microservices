package com.springsercurity.springsecurity.constants;

public class MessageConstants {

    // Common
    public static final String VALIDATION_FAILED = "Validation failed";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
    public static final String NOT_FOUND = "Not found";
    public static final String AUTHENTICATION_FAILED = "Email or password wrong";
    public static final String ACCESS_DENIED = "ACCESS_DENIED";

    // Schedule
    public static final String SCHEDULE_NOT_FOUND = "Schedule not found";
    public static final String DUPLICATE_SCHEDULE = "Schedule overlaps with existing one";
    public static final String ID_MUST_BE_NULL_ON_CREATE = "ID must not be provided when creating a new schedule";
    public static final String ID_MUST_BE_PROVIDED_ON_UPDATE = "ID must be provided when updating schedule";
    public static final String START_DATE_BEFORE_END_DATE = "Start time must be before end time";
    public static final String INVALID_ID_MATCH = "ID in path and body must match";
    public static final String WORK_SCHEDULE_UPDATED = "Work schedule updated successfully";
    public static final String WORK_SCHEDULE_FOUND = "Work schedule found";
    public static final String WORK_SCHEDULE_LIST = "Work schedule list";
    public static final String WORK_SCHEDULE_DELETED = "Work schedule deleted successfully";
    public static final String WORK_SCHEDULE_CREATED = "Work schedule created successfully";
    public static final String ALL_WORK_SCHEDULES_RETRIEVED = "All work schedules retrieved";


    // Schedule Field Validations
    public static final String JOB_NAME_BLANK = "Job name must not be blank";
    public static final String EMPLOYEE_NAME_BLANK = "Employee name must not be blank";
    public static final String START_TIME_NULL = "Start time must not be null";
    public static final String END_TIME_NULL = "End time must not be null";

    // User/Auth
    public static final String EMAIL_EXISTED = "Email already exists";
    public static final String FIRST_NAME_REQUIRED = "First name must not be blank";
    public static final String LAST_NAME_REQUIRED = "Last name must not be blank";
    public static final String EMAIL_REQUIRED = "Email must not be blank";
    public static final String EMAIL_INVALID = "Email is not valid";
    public static final String PASSWORD_REQUIRED = "Password must not be blank";
    public static final String USER_NOT_FOUND_WITH_EMAIL = "User not found with email: ";
    public static final String CURRENT_PASSWORD_NOT_BLANK = "Current password must not be blank";
    public static final String NEW_PASSWORD_NOT_BLANK = "New password must not be blank";
    public static final String NEW_PASSWORD_SIZE = "New password must be at least 6 characters";
    public static final String CONFIRMATION_PASSWORD_NOT_BLANK = "Confirmation password must not be blank";

    public static final String USER_ROLE_UPDATED = "User role updated";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
    public static final String ALL_USERS_FETCHED = "All users fetched";
    public static final String USER_FETCHED_SUCCESSFULLY = "User fetched successfully";
    public static final String MANAGER_CANNOT_DELETE_ADMIN = "Managers can only delete users with role USER.";
    public static final String USER_CAN_ONLY_VIEW_OWN_INFO = "User can only view their own information.";




    public static final String PASSWORD_CHANGE_SUCCESS = "Password changed successfully";
    public static final String WRONG_CURRENT_PASSWORD = "Wrong current password";
    public static final String NEW_PASSWORD_CONFIRMATION_MISMATCH = "New password and confirmation do not match";

    public static final String PASSWORD_RESET_SUCCESS = "Password reset successfully for user id: ";
    public static final String USER_NOT_FOUND_BY_ID = "User not found with id: ";
    public static final String MANAGER_CANNOT_RESET_NON_USER = "Managers can only reset passwords for users with role USER.";

    public static final String NO_PERMISSION_TO_RESET_PASSWORD = "You don't have permission to reset passwords.";

}
