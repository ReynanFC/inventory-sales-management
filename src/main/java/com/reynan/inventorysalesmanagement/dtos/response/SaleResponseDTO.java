package com.reynan.inventorysalesmanagement.dtos.response;

import com.reynan.inventorysalesmanagement.entities.enums.SaleStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record SaleResponseDTO(

        Long id,
        String customerName,
        SaleStatus saleStatus,
        BigDecimal totalAmount,
        List<SaleItemResponseDTO> items,
        LocalDateTime createdAt
){}
