package com.example.demo.entity;

import java.util.Set;

public enum Role {
    ADMIN(Set.of(Permission.READ_REPORTS, Permission.WRITE_REPORTS, Permission.MANAGE_USERS)),
    OPERATOR(Set.of(Permission.READ_REPORTS, Permission.WRITE_REPORTS)),
    AUDITOR(Set.of(Permission.READ_REPORTS));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
