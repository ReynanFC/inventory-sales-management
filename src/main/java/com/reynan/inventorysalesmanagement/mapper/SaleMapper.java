package com.reynan.inventorysalesmanagement.mapper;

import com.reynan.inventorysalesmanagement.dtos.request.SaleRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.response.SaleResponseDTO;
import com.reynan.inventorysalesmanagement.entities.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = SaleItemMapper.class)
public interface SaleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "saleItems", source = "items")
    @Mapping(target = "createdAt", ignore = true)
    Sale toEntity(SaleRequestDTO requestDTO);

    @Mapping(target = "customerName", source = "customer.name")
    @Mapping(target = "totalAmount", source = "totalAmount")
    @Mapping(target = "items", source = "saleItems")
    SaleResponseDTO toResponseDTO(Sale entity);

    List<SaleResponseDTO> toListResponseDTO(List<Sale> saleList);
}
