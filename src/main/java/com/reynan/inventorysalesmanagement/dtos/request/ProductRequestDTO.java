package com.reynan.inventorysalesmanagement.dtos.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequestDTO(

    @NotBlank(message = "Name is required")
    @Size(max = 150, message = "Maximum of 150 characters")
    String name,

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Maximum of 255 characters")
    String description,

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.00", message = "Price must be equal or greater than 0")
    BigDecimal price,

    @NotNull(message = "Minimum stock is required")
    @Min(value = 1, message = "Minimum stock must be equal or greater than 0")
    Integer minimumStock,

    @NotNull(message = "Category ID is required")
    Long categoryId

) {}
