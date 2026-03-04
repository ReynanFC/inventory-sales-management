package com.reynan.inventorysalesmanagement.dtos.response;

import java.math.BigDecimal;

public record SaleItemResponseDTO (
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice
) {}
