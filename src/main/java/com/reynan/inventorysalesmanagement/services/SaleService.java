package com.reynan.inventorysalesmanagement.services;

import com.reynan.inventorysalesmanagement.dtos.request.SaleRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.request.UpdateSaleStatusRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.response.SaleResponseDTO;
import com.reynan.inventorysalesmanagement.entities.*;
import com.reynan.inventorysalesmanagement.entities.enums.MovementType;
import com.reynan.inventorysalesmanagement.entities.enums.SaleStatus;
import com.reynan.inventorysalesmanagement.exceptions.ResourceNotFoundException;
import com.reynan.inventorysalesmanagement.mapper.SaleMapper;
import com.reynan.inventorysalesmanagement.repository.CustomerRepository;
import com.reynan.inventorysalesmanagement.repository.ProductRepository;
import com.reynan.inventorysalesmanagement.repository.SaleRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SaleService {

    public final Logger logger = LoggerFactory.getLogger(SaleService.class);
    public final SaleRepository saleRepository;
    public final CustomerRepository customerRepository;
    public final ProductRepository productRepository;
    public final SaleMapper mapperSale;
    private final SaleMapper saleMapper;

    public SaleService(SaleRepository saleRepository, SaleMapper mapperSale, CustomerRepository customerRepository
    , ProductRepository productRepository, SaleMapper saleMapper) {
        this.saleRepository = saleRepository;
        this.mapperSale = mapperSale;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.saleMapper = saleMapper;
    }

    public SaleResponseDTO findById(Long id) {

        logger.debug("Finding Sale with id: " + id);

        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with ID: " + id));

        logger.info("Sale found with id: {}", id);

        return saleMapper.toResponseDTO(sale);
    }

    @Transactional
    public SaleResponseDTO create(SaleRequestDTO saleRequestDTO) {

        logger.info("Creating new sale for customer id: {}", saleRequestDTO.customerId());
        Customer customer = customerRepository.findById(saleRequestDTO.customerId())
                .orElseThrow(
                     () -> new ResourceNotFoundException("Customer not found with ID: " + saleRequestDTO.customerId())
                );

        Sale sale = new Sale(customer, SaleStatus.PENDING);

        saleRequestDTO.items().forEach(item -> {
            logger.debug("Processing product id: {} with quantity: {}",
                    item.productId(), item.quantity());

           Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + item.productId()));

            sale.addSaleItem(new SaleItem(product, item.quantity(), product.getPrice()));
            product.addStockMoviment(new StockMovement(item.quantity(), MovementType.EXIT));
        });

        saleRepository.save(sale);
        logger.info("Sale created successfully with id: {}", sale.getId());
        return saleMapper.toResponseDTO(sale);
    }

    @Transactional
    public void updateStatus (Long id, UpdateSaleStatusRequestDTO status) {
        logger.info("Updating status of sale id: {} to {}", id, status.saleStatus());

        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with ID: " + id));

        SaleStatus currentStatus = sale.getSaleStatus();
        SaleStatus newStatus = status.saleStatus();

        if (currentStatus == newStatus) {
            logger.info("Update ignored: sale id {} already has the status {}", id, currentStatus);
            return;
        }

        if (!canTransitionTo(newStatus, currentStatus)){
            throw new IllegalArgumentException("Invalid status transition");
        }

        if (newStatus == SaleStatus.CANCELED) {
            revertStock(sale);
        }

        sale.setSaleStatus(status.saleStatus());

        logger.info("Sale id: {} updated from {} to {}", id,
                sale.getSaleStatus(), newStatus);
    }

    public boolean canTransitionTo(SaleStatus newStatus, SaleStatus currentStatus) {

        if (currentStatus == SaleStatus.PENDING &&
                (newStatus == SaleStatus.COMPLETED || newStatus == SaleStatus.CANCELED)) {
            return true;
        }

        return false;
    }

    public void revertStock(Sale sale) {
        logger.info("Reverting stock for canceled sale id: {}", sale.getId());

        sale.getSaleItems().forEach(saleItem -> {
            logger.debug("Reverting product id: {} | quantity: {}",
                    saleItem.getProduct().getId(),
                    saleItem.getQuantity());

            saleItem.getProduct().
                    addStockMoviment(
                            new StockMovement(saleItem.getQuantity(), MovementType.ENTRY));
        });
    }

    public Page<SaleResponseDTO> findAll(Pageable pageable) {
        logger.debug("Fetching sales page: {}, size: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Sale> sales = saleRepository.findAll(pageable);

        logger.info("Sales page retrieved successfully");
        return sales.map(saleMapper :: toResponseDTO);
    }

}
