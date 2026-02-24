package com.reynan.inventorysalesmanagement.services;

import com.reynan.inventorysalesmanagement.dtos.request.CustomerRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.response.CustomerResponseDTO;
import com.reynan.inventorysalesmanagement.entities.Customer;
import com.reynan.inventorysalesmanagement.exceptions.ResourceNotFoundException;
import com.reynan.inventorysalesmanagement.mapper.CustomerMapper;
import com.reynan.inventorysalesmanagement.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerService {

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerMapper mapper;
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerMapper mapper, CustomerRepository customerRepository) {
        this.mapper = mapper;
        this.customerRepository = customerRepository;
    }

    public CustomerResponseDTO findById(long id) {
        logger.debug("Finding Customer with id: {}", id);

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

        return mapper.toResponseDTO(customer);
    }

    @Transactional
    public CustomerResponseDTO create(CustomerRequestDTO customerRequestDTO) {

        logger.debug("Creating new Customer: {}", customerRequestDTO.name());

        Customer customer = mapper.toEntity(customerRequestDTO);
        customerRepository.save(customer);

        return mapper.toResponseDTO(customer);
    }

    @Transactional
    public CustomerResponseDTO update(long id, CustomerRequestDTO customerRequestDTO) {

        logger.debug("Updating Customer with id: {}", id);

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

        logger.debug("Updating Customer: {}", customerRequestDTO.name());

        customer.setName(customerRequestDTO.name());
        customer.setEmail(customerRequestDTO.email());
        customer.setTelephone(customerRequestDTO.telephone());

        return mapper.toResponseDTO(
                customerRepository.save(customer)
        );
    }

        @Transactional
        public void delete(Long id) {

            logger.debug("Deleting Customer with id: {}", id);

            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

            customerRepository.delete(customer);
        }

        public Set<CustomerResponseDTO> findAll() {

            return mapper.toSetResponseDTO(new HashSet<>(customerRepository.findAll()));
        }

//        public List<CustomerResponseDTO> relatedSales() {
//
//
//        }

}
