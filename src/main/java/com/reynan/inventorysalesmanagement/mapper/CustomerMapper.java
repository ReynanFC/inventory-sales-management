package com.reynan.inventorysalesmanagement.mapper;

import com.reynan.inventorysalesmanagement.dtos.request.CustomerRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.response.CustomerResponseDTO;
import com.reynan.inventorysalesmanagement.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sales", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Customer toEntity(CustomerRequestDTO requestDTO);

    CustomerResponseDTO toResponseDTO(Customer entity);

    Set<CustomerResponseDTO> toSetResponseDTO(Set<Customer> entity);
}
