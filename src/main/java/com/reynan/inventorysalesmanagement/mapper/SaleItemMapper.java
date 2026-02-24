package com.reynan.inventorysalesmanagement.mapper;

import com.reynan.inventorysalesmanagement.dtos.request.SaleItemRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.response.SaleItemResponseDTO;
import com.reynan.inventorysalesmanagement.entities.SaleItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface SaleItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "unitPrice", ignore = true)
    @Mapping(target = "sale", ignore = true)
    SaleItem toEntity(SaleItemRequestDTO requestDTO);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    SaleItemResponseDTO  toResponseDTO(SaleItem entity);
}
