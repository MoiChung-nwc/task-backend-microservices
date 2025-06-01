package com.chung.authservice.constants;

public class MessageConstants {
    /**
     * Tiền tố role trong chuỗi, ví dụ "ROLE_ADMIN".
     */
    public static final String ROLE_PREFIX = "ROLE_";

    /**
     * Độ dài của tiền tố role "ROLE_".
     */
    public static final int ROLE_PREFIX_LENGTH = ROLE_PREFIX.length();

    /**
     * Ký tự phân tách các role trong chuỗi, ví dụ "ADMIN,USER".
     */
    public static final String ALLOWED_ROLES_SEPARATOR = ",";

    /**
     * Log thông tin khi bắt đầu kiểm tra quyền.
     * Format với: serviceName, apiPath, httpMethod, role
     */
    public static final String LOG_CHECKING_PERMISSION = "[checkPermission] Checking permission for serviceName=%s, apiPath=%s, httpMethod=%s, role=%s";

    /**
     * Log danh sách quyền lấy từ DB.
     */
    public static final String LOG_PERMISSIONS_FROM_DB = "[checkPermission] Permissions from DB:";

    /**
     * Log khi kiểm tra từng bản ghi quyền.
     */
    public static final String LOG_PERMISSION_RECORD = "[checkPermission] Checking permission record:";

    /**
     * Log kiểm tra phương thức HTTP có khớp.
     * Format với: httpMethod, kết quả boolean
     */
    public static final String LOG_DB_METHOD_MATCH = "  HttpMethod DB: %s, matches? %s";

    /**
     * Log kiểm tra pattern apiPath có khớp.
     * Format với: apiPathPattern, kết quả boolean
     */
    public static final String LOG_DB_PATH_MATCH = "  ApiPathPattern DB: %s, matches? %s";

    /**
     * Log role sau khi chuẩn hóa (bỏ tiền tố ROLE_ nếu có).
     */
    public static final String LOG_ROLE_NORMALIZED = "[checkPermission] Normalized request role: %s";

    /**
     * Log so sánh role được phép với role request.
     * Format với: allowedRole, normalized requestRole
     */
    public static final String LOG_ROLE_COMPARISON = "[checkPermission] Comparing allowedRole='%s' with normalized requestRole='%s'";

    /**
     * Log khi quyền được cấp.
     */
    public static final String LOG_PERMISSION_GRANTED = "[checkPermission] Permission GRANTED.";

    /**
     * Log khi quyền bị từ chối.
     */
    public static final String LOG_PERMISSION_DENIED = "[checkPermission] Permission DENIED.";
}
