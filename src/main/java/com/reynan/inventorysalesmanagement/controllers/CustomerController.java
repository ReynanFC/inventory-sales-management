package com.reynan.inventorysalesmanagement.controllers;

import com.reynan.inventorysalesmanagement.dtos.request.CustomerRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.response.CustomerResponseDTO;
import com.reynan.inventorysalesmanagement.dtos.response.SaleResponseDTO;
import com.reynan.inventorysalesmanagement.services.CustomerService;
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
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable Long id){

        return ResponseEntity.ok().body(customerService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponseDTO>> findAll(
            @ParameterObject @PageableDefault(
                    page = 0, size = 10, sort = "name", direction = Sort.Direction.DESC
            ) Pageable pageable){

        return ResponseEntity.ok().body(customerService.findAll(pageable));
    }

    @GetMapping("/{id}/sales")
    public ResponseEntity<Page<SaleResponseDTO>> relatedSales(
            @PathVariable Long id, @ParameterObject @PageableDefault(
                    page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC
            ) Pageable pageable){


        return ResponseEntity.ok().body(customerService.relatedSales(id, pageable));
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(@Valid @RequestBody CustomerRequestDTO customerRequestDTO){
        CustomerResponseDTO obj = customerService.create(customerRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.id())
                .toUri();

        return ResponseEntity.created(uri).body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@Valid @RequestBody CustomerRequestDTO customerRequestDTO,
                                                      @PathVariable Long id) {

       return ResponseEntity.ok().body(customerService.update(id, customerRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){

        customerService.delete(id);
        return ResponseEntity.notFound().build();
    }
}
