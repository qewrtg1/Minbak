package com.minbak.web.spring_security.jwt;

public enum UserRole {
    ROLE_USER("user"),
    ROLE_ADMIN("admin");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    // "ROLE_USER --> "user" 형식으로 변환
    public static String fromRoleName(String name) {
        for (UserRole role : values()) {
            if (role.name().equals(name)) {
                return role.getRoleName();
            }
        }
        return name.replace("ROLE_", "").toUpperCase();
    }

    // "user" --> "ROLE_USER" 형식으로 변환
    public static String toRoleName(String roleName) {
        for (UserRole role : values()) {
            if (role.getRoleName().equalsIgnoreCase(roleName)) {
                return role.name();
            }
        }
        return "ROLE_" + roleName.toUpperCase();
    }
}