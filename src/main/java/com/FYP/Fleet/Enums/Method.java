package com.FYP.Fleet.Enums;

public enum Method {
    UPI(1,"UPI"),
    CASH(2,"CASH"),
    BANK_TRANSFER(3,"BANK_TRANSFER"),
    CHEQUE(4, "CHEQUE");

    private final int id;
    private final String label;


    Method(int id, String label) {
        this.label = label;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
