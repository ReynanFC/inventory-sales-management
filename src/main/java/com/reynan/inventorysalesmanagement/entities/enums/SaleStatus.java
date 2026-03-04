package com.reynan.inventorysalesmanagement.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SaleStatus {

    PENDING,
    COMPLETED,
    CANCELED;

    @JsonCreator
    public SaleStatus fromValue(String value) {
        try {
            return SaleStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid sale status: " + value);
        }
    }
}
