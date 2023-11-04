package com.example.casestudy.constants;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WinnerStatus {
    CONFIRMED("Confirmed"),
    PAID("Paid"),
    SHIPPED("Shipped"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private String value;
    WinnerStatus(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString(){
        return String.valueOf(value);
    }
}
