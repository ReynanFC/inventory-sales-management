package com.reynan.inventorysalesmanagement.services;

import com.reynan.inventorysalesmanagement.dtos.request.CategoryRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.response.CategoryResponseDTO;
import com.reynan.inventorysalesmanagement.entities.Category;
import com.reynan.inventorysalesmanagement.exceptions.BusinessException;
import com.reynan.inventorysalesmanagement.exceptions.ResourceNotFoundException;
import com.reynan.inventorysalesmanagement.mapper.CategoryMapper;
import com.reynan.inventorysalesmanagement.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class CategoryService {

    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryMapper mapper;
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryMapper mapper, CategoryRepository categoryRepository) {
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDTO findById(Long id) {

        logger.debug("Finding Category with id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

        return mapper.toResponseDTO(category);
    }

    @Transactional
    public CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO) {

        logger.debug("Creating new Category: {}", categoryRequestDTO.name());

        Category category = mapper.toEntity(categoryRequestDTO);
        categoryRepository.save(category);

        logger.info("Category created successfully: {}", category.getId());

        return mapper.toResponseDTO(category);
    }

    @Transactional
    public CategoryResponseDTO update(Long id, CategoryRequestDTO categoryRequestDTO) {

        logger.debug("Updating Category with id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

        category.setName(categoryRequestDTO.name());
        category.setDescription(categoryRequestDTO.description());

        logger.info("Category updated successfully: {}", category.getId());

        return mapper.toResponseDTO(
                categoryRepository.save(category)
        );
    }

    @Transactional
    public void delete(Long id) {

        logger.debug("Finding Category with id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

        logger.debug("Checking if contains products in category: {}", category.getName());

        if(!category.getProducts().isEmpty()) {
            throw new BusinessException("Cannot delete category because it has associated products.");
        }

        logger.info("Deleting Category with id: {}", id);
        categoryRepository.delete(category);
    }

    public Set<CategoryResponseDTO> findAll() {

        logger.debug("Finding all Categories");

        return mapper.toSetResponseDTO(
                new HashSet<>(categoryRepository.findAll())
        );
    }
}