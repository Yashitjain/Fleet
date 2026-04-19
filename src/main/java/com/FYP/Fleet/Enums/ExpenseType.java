package com.FYP.Fleet.Enums;

public enum ExpenseType {
    DIESEL(1, "Diesel"),
    TOLL(2, "Toll"),
    DRIVER(3, "Driver"),
    OTHER(4, "other");

    private final int id;
    private final String label;

    ExpenseType(int id, String label){
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
