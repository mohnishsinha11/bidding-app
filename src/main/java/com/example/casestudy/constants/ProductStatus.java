package com.example.casestudy.constants;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductStatus {
    DRAFT("Draft"),
    ACTIVE("Active"),
    CLOSED("Closed");

    private String value;
    ProductStatus(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString(){
        return String.valueOf(value);
    }
}
