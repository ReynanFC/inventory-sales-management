package com.reynan.inventorysalesmanagement.mapper;

import com.reynan.inventorysalesmanagement.dtos.response.StockMovementResponseDTO;
import com.reynan.inventorysalesmanagement.entities.StockMovement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface StockMovementMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    StockMovementResponseDTO toResponseDTO(StockMovement entity);
}
