package com.reynan.inventorysalesmanagement.mapper;

import com.reynan.inventorysalesmanagement.dtos.response.ProductDetailResponseDTO;
import com.reynan.inventorysalesmanagement.dtos.response.ProductResponseDTO;
import com.reynan.inventorysalesmanagement.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "quantityInStock", ignore = true)
    @Mapping(target = "stockMovements", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductDetailResponseDTO requestDTO);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    ProductDetailResponseDTO toDetailResponseDTO(Product entity);

    ProductResponseDTO toResponseDTO(Product entity);

    Set<ProductResponseDTO> toSetResponseDTO(Set<Product> entities);
}
