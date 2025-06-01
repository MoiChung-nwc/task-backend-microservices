package com.springsercurity.springsecurity.security;

public enum Permission {

    GET_USER("GET", "/api/users/**"),
    GET_USER_BY_ID("GET", "/api/users/**"),
    CREATE_USER("POST", "/api/users"),
    UPDATE_USER("PUT", "/api/users/**"),
    DELETE_USER("DELETE", "/api/users/**");

    private final String method;
    private final String path;

    Permission(String method, String path) {
        this.method = method;
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

}
