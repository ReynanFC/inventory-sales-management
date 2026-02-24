package com.reynan.inventorysalesmanagement.dtos.response;

import java.math.BigDecimal;

public record ProductResponseDTO(
    Long id,
    String name,
    BigDecimal price,
    Integer quantityInStock
) {}
