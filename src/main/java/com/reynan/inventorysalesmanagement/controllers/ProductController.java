package com.reynan.inventorysalesmanagement.controllers;

import com.reynan.inventorysalesmanagement.dtos.request.ProductRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.request.StockMovementRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.response.ProductDetailResponseDTO;
import com.reynan.inventorysalesmanagement.dtos.response.ProductResponseDTO;
import com.reynan.inventorysalesmanagement.dtos.response.StockMovementResponseDTO;
import com.reynan.inventorysalesmanagement.services.ProductService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponseDTO> findById(@PathVariable Long id) {

        return ResponseEntity.ok().body(productService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> productFindAll(
            @ParameterObject @PageableDefault(
                    page = 0, size = 10, sort = "name", direction = Sort.Direction.DESC
            ) Pageable pageable) {

        return ResponseEntity.ok().body(productService.findAll(pageable));
    }

    @GetMapping("/{id}/stock-movements")
    public ResponseEntity<Page<StockMovementResponseDTO>> showStockMoviments(
            @PathVariable Long id, @ParameterObject @PageableDefault(
             page = 0, size = 10, sort = "movementDate", direction = Sort.Direction.DESC
            ) Pageable pageable) {

        return ResponseEntity.ok().body(productService.relatedMovements(id, pageable));
    }

    @GetMapping("/by-category")
    public ResponseEntity<Page<ProductResponseDTO>> showProductsByCategory(@RequestParam Long categoryId,
           @ParameterObject @PageableDefault(
                    page = 0, size = 10, sort = "name", direction = Sort.Direction.DESC
           ) Pageable pageable) {

        return ResponseEntity.ok().body(productService.showProductByCategory(categoryId, pageable));
    }

    @PostMapping
    public ResponseEntity<ProductDetailResponseDTO> create(@Valid @RequestBody ProductRequestDTO productRequestDTO) {

        ProductDetailResponseDTO obj = productService.create(productRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.id())
                .toUri();

        return ResponseEntity.created(uri).body(obj);
    }

    @PostMapping("/{id}/stock")
    public ResponseEntity<?> addStock(@PathVariable Long id,
                                      @Valid @RequestBody StockMovementRequestDTO stockMovementRequestDTO) {

        productService.addStock(id, stockMovementRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDetailResponseDTO> update(@PathVariable Long id,
                                                           @Valid @RequestBody ProductRequestDTO  productRequestDTO) {
        return ResponseEntity.ok().body(productService.update(id, productRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);
       return ResponseEntity.noContent().build();
    }
}
