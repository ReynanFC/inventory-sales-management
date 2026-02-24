package com.reynan.inventorysalesmanagement.dtos.response;

import java.util.List;

public record CustomerDetailResponseDTO (
        List<SaleResponseDTO> sales
) {}
