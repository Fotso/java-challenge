package jp.co.axa.apidemo.security;

/**
 * ApplicationUserPermission
 * Enum class that defines user's permission
 * or user's authorities.
 */
public enum ApplicationUserPermission {

    EMPLOYEE_EDIT("employee:edit"),
    EMPLOYEE_READ("employee:read"),
    EMPLOYEE_WRITE("employee:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
