package com.reynan.inventorysalesmanagement.controllers;

import com.reynan.inventorysalesmanagement.dtos.request.SaleRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.request.UpdateSaleStatusRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.response.SaleResponseDTO;
import com.reynan.inventorysalesmanagement.services.SaleService;
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
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> findById(@PathVariable Long id) {

        return ResponseEntity.ok().body(saleService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<SaleResponseDTO>> findAll(
           @ParameterObject @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {

        return  ResponseEntity.ok().body(saleService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<SaleResponseDTO> create(@Valid @RequestBody SaleRequestDTO saleRequestDTO) {

        SaleResponseDTO obj = saleService.create(saleRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.id())
                .toUri();

        return ResponseEntity.created(uri).body(obj);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateStatus(@Valid @RequestBody UpdateSaleStatusRequestDTO statusDTO,
                                          @PathVariable Long id) {
        saleService.updateStatus(id, statusDTO);

        return ResponseEntity.noContent().build();
    }
}
