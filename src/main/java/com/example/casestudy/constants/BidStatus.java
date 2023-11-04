package com.example.casestudy.constants;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BidStatus {
    ACTIVE("Active"),
    INVALID("Invalid");

    private String value;
    BidStatus(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString(){
        return String.valueOf(value);
    }
}
