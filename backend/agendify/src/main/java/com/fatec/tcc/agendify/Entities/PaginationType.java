package com.fatec.tcc.agendify.Entities;

public enum PaginationType {
    SNA(1, "sort username from user asc"),
    SND(2, "sort username from user desc"),
    SBA(3, "sort birthday from user asc"),
    SBD(4, "sort birthday from user desc");

    private final int value;
    private final String description;

    PaginationType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return this.value;
    }
    public String getDescription() {
        return this.description;
    }

    public static boolean paginationTypeExists(String isEnum) {
        for (PaginationType pt : PaginationType.values()) {
            if (pt.name().equalsIgnoreCase(isEnum))
                return true;
        }
        return false;
    }

    public static boolean paginationTypeExists(int isEnum) {
        for (PaginationType pt : PaginationType.values()) {
            if (pt.getValue() == isEnum)
                return true;
        }
        return false;
    }

}
