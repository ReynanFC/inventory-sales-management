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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        logger.debug("Finding all Customers");
            return mapper.toSetResponseDTO(new HashSet<>(customerRepository.findAll()));
        }

        public List<SaleResponseDTO> relatedSales(Long id) {

        logger.debug("Finding related Sales for Customer with id: {}", id);
        List<Sale> saleList = saleRepository.findByCustomerId(id);

        return saleMapper.toListResponseDTO(saleList);

        }

}
