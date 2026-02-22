package com.reynan.inventorysalesmanagement.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerResponseDTO(

        Long id,
        String name,
        String email,
        String telephone,
        LocalDate createdAt

){}
