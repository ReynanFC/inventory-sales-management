package com.reynan.inventorysalesmanagement.dtos.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryResponseDTO(

        Long id,
        String name,
        String description
) {}
