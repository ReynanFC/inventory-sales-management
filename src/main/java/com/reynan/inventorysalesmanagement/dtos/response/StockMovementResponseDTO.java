package com.reynan.inventorysalesmanagement.dtos.response;

import com.reynan.inventorysalesmanagement.entities.enums.MovementType;

import java.time.LocalDateTime;

public record StockMovementResponseDTO (

        Long id,
        Long productId,
        String productName,
        Integer quantity,
        MovementType movementType,
        LocalDateTime movementDate
) {}
