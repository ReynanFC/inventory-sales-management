package com.reynan.inventorysalesmanagement.services;

import com.reynan.inventorysalesmanagement.dtos.request.CustomerRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.response.CustomerResponseDTO;
import com.reynan.inventorysalesmanagement.dtos.response.SaleResponseDTO;
import com.reynan.inventorysalesmanagement.entities.Customer;
import com.reynan.inventorysalesmanagement.entities.Sale;
import com.reynan.inventorysalesmanagement.exceptions.ResourceNotFoundException;
import com.reynan.inventorysalesmanagement.mapper.CustomerMapper;
import com.reynan.inventorysalesmanagement.mapper.SaleMapper;
import com.reynan.inventorysalesmanagement.repository.CustomerRepository;
import com.reynan.inventorysalesmanagement.repository.SaleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerMapper mapper;
    private final SaleMapper saleMapper;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;

    public CustomerService(CustomerMapper mapper, CustomerRepository customerRepository,
                           SaleRepository saleRepository, SaleMapper saleMapper) {
        this.mapper = mapper;
        this.saleMapper = saleMapper;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;
    }

    public CustomerResponseDTO findById(long id) {
        logger.debug("Finding Customer with id: {}", id);

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

        return mapper.toResponseDTO(customer);
    }

    @Transactional
    public CustomerResponseDTO create(CustomerRequestDTO customerRequestDTO) {

        logger.info("Creating new Customer: {}", customerRequestDTO.name());

        Customer customer = mapper.toEntity(customerRequestDTO);
        customerRepository.save(customer);

        return mapper.toResponseDTO(customer);
    }

    @Transactional
    public CustomerResponseDTO update(long id, CustomerRequestDTO customerRequestDTO) {

        logger.info("Updating Customer with id: {}", id);

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

        customer.setName(customerRequestDTO.name());
        customer.setEmail(customerRequestDTO.email());
        customer.setTelephone(customerRequestDTO.telephone());

        return mapper.toResponseDTO(customer);
    }

        @Transactional
        public void delete(Long id) {

            logger.debug("Finding Customer with id: {}", id);

            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

            logger.info("Deleting Customer with id: {}", id);

            customerRepository.delete(customer);
        }

        public Page<CustomerResponseDTO> findAll(Pageable pageable) {

            logger.debug("Fetching customers | page={} | size={}",
                    pageable.getPageNumber(),
                    pageable.getPageSize());

            Page<Customer> customers = customerRepository.findAll(pageable);

        if (customers.isEmpty()) {
            logger.info("No customers found");
        }

        return customers.map(mapper::toResponseDTO);
        }

        public Page<SaleResponseDTO> relatedSales(Long id, Pageable pageable) {

            logger.debug("Fetching sales for customer | customerId={} | page={} | size={}",
                    id,
                    pageable.getPageNumber(),
                    pageable.getPageSize());

        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with ID: " + id);
        }

        Page<Sale> sales = saleRepository.findByCustomerId(id, pageable);

        if (sales.isEmpty()) {
            logger.info("No sales found for customer id: {}", id);
        }

        return sales.map(saleMapper :: toResponseDTO);
        }
}
