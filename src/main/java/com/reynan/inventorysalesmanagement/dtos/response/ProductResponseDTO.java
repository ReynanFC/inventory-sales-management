package com.reynan.inventorysalesmanagement.dtos.response;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDTO(

        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer quantityInStock,
        Integer minimumStock,
        Long categoryId,
        String categoryName,
        LocalDateTime createdAt
) {}