package com.reynan.inventorysalesmanagement.dtos.request;

import com.reynan.inventorysalesmanagement.entities.enums.SaleStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateSaleStatusRequestDTO (

        @NotNull(message = "Sale status is required")
        SaleStatus saleStatus
) {}
