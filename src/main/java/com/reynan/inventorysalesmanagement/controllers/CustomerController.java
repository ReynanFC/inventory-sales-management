package com.reynan.inventorysalesmanagement.controllers;

import com.reynan.inventorysalesmanagement.dtos.request.CustomerRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.response.CustomerResponseDTO;
import com.reynan.inventorysalesmanagement.dtos.response.SaleResponseDTO;
import com.reynan.inventorysalesmanagement.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
    public ResponseEntity<List<CustomerResponseDTO>> findAll(){

        List<CustomerResponseDTO> customers = customerService.findAll()
                .stream()
                .toList();

        return ResponseEntity.ok().body(customers);
    }

    @GetMapping("/{id}/sales")
    public ResponseEntity<List<SaleResponseDTO>> relatedSales(@PathVariable Long id){

        List<SaleResponseDTO> sales = customerService.relatedSales(id);

        return ResponseEntity.ok().body(sales);
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
