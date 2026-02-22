package com.reynan.inventorysalesmanagement.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerRequestDTO(

    @NotBlank(message = "Name is required")
    @Size(max = 30, min = 3, message = "Maximum of 30 characters")
    String name,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 225)
    String email,

    @Size(min = 10)
    @Pattern(
            regexp = "^\\([1-9]{2}\\) (?:[2-8]|9[0-9])[0-9]{3}\\-[0-9]{4}$",
            message = "The phone number must be in the format: (DD) 9XXXX-XXXX"
    )
    String telephone
) {}
