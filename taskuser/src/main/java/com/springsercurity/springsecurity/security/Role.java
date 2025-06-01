package com.springsercurity.springsecurity.security;

import java.util.Collections;
import java.util.Set;

public enum Role {

    USER(
            Set.of(
                    //Permission.CREATE_USER,
                    Permission.GET_USER_BY_ID,
                    Permission.UPDATE_USER
            )
    ),
    ADMIN(
            Set.of(
                    Permission.GET_USER,
                    Permission.GET_USER_BY_ID,
                    Permission.CREATE_USER,
                    Permission.UPDATE_USER,
                    Permission.DELETE_USER
            )
    ),
    MANAGER(
            Set.of(
                    Permission.GET_USER,
                    Permission.GET_USER_BY_ID,
                    Permission.CREATE_USER,
                    Permission.UPDATE_USER,
                    Permission.DELETE_USER
            )
    );

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = Collections.unmodifiableSet(permissions);
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

}
