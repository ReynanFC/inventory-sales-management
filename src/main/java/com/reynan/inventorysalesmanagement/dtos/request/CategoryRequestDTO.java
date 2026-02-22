package com.reynan.inventorysalesmanagement.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(

    @NotBlank(message = "Name is required")
    @Size(max = 30)
    String name,

    @NotBlank(message = "Description is required")
    @Size(max = 225)
    String description
) {}
