package com.reynan.inventorysalesmanagement.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDetailResponseDTO(

        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer quantityInStock,
        Integer minimumStock,
        Long categoryId,
        String categoryName,
        LocalDateTime createdAt,
        LocalDateTime updateAt
) {}