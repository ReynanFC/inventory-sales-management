package com.reynan.inventorysalesmanagement.services;

import com.reynan.inventorysalesmanagement.dtos.request.CategoryRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.response.CategoryResponseDTO;
import com.reynan.inventorysalesmanagement.entities.Category;
import com.reynan.inventorysalesmanagement.mapper.CategoryMapper;
import com.reynan.inventorysalesmanagement.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private CategoryMapper mapper;
    private CategoryRepository categoryRepository;

    public CategoryResponseDTO findById(Long id) {

        logger.debug("Finding Category with id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> );

        return mapper.toResponseDTO(category);
    }

    public CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO) {

        logger.debug("Creating new Category: {}", categoryRequestDTO.name());

        Category category = mapper.toEntity(categoryRequestDTO);
        categoryRepository.save(category);

        return mapper.toResponseDTO(category);
    }

    public CategoryResponseDTO update(Long id, CategoryRequestDTO categoryRequestDTO) {

        logger.debug("Updating Category with id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> );

        logger.debug("Updating Category: {}", categoryRequestDTO.name());

        category.setName(categoryRequestDTO.name());
        category.setDescription(categoryRequestDTO.description());

        return mapper.toResponseDTO(
                categoryRepository.save(category)
        );
    }

    public void delete(Long id) {
        logger.debug("Deleting Category with id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->);

        categoryRepository.delete(category);
    }

    public CategoryResponseDTO addProductToCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        logger.debug("Adding Product to Category with id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->);
    }

}
