package com.reynan.inventorysalesmanagement.dtos.request;

import com.reynan.inventorysalesmanagement.entities.enums.SaleStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record SaleRequestDTO(

        @NotNull(message = "Customer ID is required")
        Long customerId,

        @NotEmpty(message = "A sale must have at least one item")
        List<SaleItemRequestDTO> items

) {}
