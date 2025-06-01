package com.chung.gateway.constants;

public class MessageConstant {
    public static final String UNKNOWN_SERVICE = "unknown-service";
    public static final String AUTH_CHECK_PERMISSION_URI = "/auth/check-permission";
    public static final String EMPTY_STRING = "";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final int ROLE_PREFIX_LENGTH = ROLE_PREFIX.length();
    public static final String SEPARATOR_COLON_SPACE = ": ";
    public static final String LOG_AUTH_RESPONSE = "[AuthPermissionFilter] Auth service response for role=";
    public static final String LOG_AUTH_ERROR = "[AuthPermissionFilter] Error calling auth service for role=";
    public static final String FIELD_SERVICE_NAME = "serviceName";
    public static final String FIELD_API_PATH = "apiPath";
    public static final String FIELD_HTTP_METHOD = "httpMethod";
    public static final String FIELD_ROLE = "role";
    public static final int FILTER_ORDER = -1;
}
