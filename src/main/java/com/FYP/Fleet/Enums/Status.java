package com.FYP.Fleet.Enums;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE(1, "Active"),
    COMPLETED(2, "Completed");

    private final int id;
    private final String label;

    Status(int id, String label){
        this.id = id;
        this.label = label;
    }

}
