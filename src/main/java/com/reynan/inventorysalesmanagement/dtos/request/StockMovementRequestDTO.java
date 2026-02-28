package com.reynan.inventorysalesmanagement.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StockMovementRequestDTO(

    @NotNull
    @Positive
    Integer quantity
){}
