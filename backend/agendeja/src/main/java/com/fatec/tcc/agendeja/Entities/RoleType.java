package com.fatec.tcc.agendeja.Entities;

public enum RoleType {
    USER(1890, "Role for regular user"),
    ADMIN(2547, "Role for admin"),
    ENTERPRISE(3281, "Role for job provider/enterprise user");

    private final int value;
    private final String description;

    RoleType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return this.value;
    }
    public String getDescription() {
        return this.description;
    }

    public static boolean roleTypeExists(String isEnum) {
        for (RoleType pt : RoleType.values()) {
            if (pt.name().equalsIgnoreCase(isEnum))
                return true;
        }
        return false;
    }

    public static boolean roleTypeExists(int isEnum) {
        for (RoleType pt : RoleType.values()) {
            if (pt.getValue() == isEnum)
                return true;
        }
        return false;
    }

}
