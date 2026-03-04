package com.reynan.inventorysalesmanagement.controllers;

import com.reynan.inventorysalesmanagement.dtos.request.CategoryRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.response.CategoryResponseDTO;
import com.reynan.inventorysalesmanagement.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable("id") Long id) {

        return ResponseEntity.ok().body(categoryService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findAll() {

        List<CategoryResponseDTO> categories = categoryService.findAll()
                .stream()
                .toList();

        return ResponseEntity.ok().body(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {

        CategoryResponseDTO obj = categoryService.create(categoryRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.id())
                .toUri();

        return ResponseEntity.created(uri).body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO,
                                                      @PathVariable Long id) {

        return ResponseEntity.ok().body(categoryService.update(id, categoryRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
