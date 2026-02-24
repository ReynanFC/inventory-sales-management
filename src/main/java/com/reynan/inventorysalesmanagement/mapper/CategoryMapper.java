package com.reynan.inventorysalesmanagement.mapper;

import com.reynan.inventorysalesmanagement.dtos.request.CategoryRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.response.CategoryDetailResponseDTO;
import com.reynan.inventorysalesmanagement.dtos.response.CategoryResponseDTO;
import com.reynan.inventorysalesmanagement.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toEntity(CategoryRequestDTO requestDTO);

    CategoryResponseDTO toResponseDTO(Category category);
    CategoryDetailResponseDTO toDetailResponseDTO(Category category);

    Set<CategoryResponseDTO> toSetResponseDTO(Set<Category> entities);
}
