package com.reynan.inventorysalesmanagement.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerResponseDTO(

        Long id,
        String name,
        String email,
        String telephone,
        LocalDate createdAt,
        LocalDateTime updatedAt
){}
