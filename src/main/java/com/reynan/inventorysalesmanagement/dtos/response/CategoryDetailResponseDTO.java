package com.reynan.inventorysalesmanagement.dtos.response;

import java.util.Set;

public record CategoryDetailResponseDTO(
        Long id,
        String name,
        String description,
        Set<ProductResponseDTO> products
) {}
