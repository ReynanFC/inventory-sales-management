package com.reynan.inventorysalesmanagement.services;

import com.reynan.inventorysalesmanagement.dtos.request.ProductRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.request.StockMovementRequestDTO;
import com.reynan.inventorysalesmanagement.dtos.response.ProductDetailResponseDTO;
import com.reynan.inventorysalesmanagement.dtos.response.ProductResponseDTO;
import com.reynan.inventorysalesmanagement.dtos.response.StockMovementResponseDTO;
import com.reynan.inventorysalesmanagement.entities.Category;
import com.reynan.inventorysalesmanagement.entities.Product;
import com.reynan.inventorysalesmanagement.entities.StockMovement;
import com.reynan.inventorysalesmanagement.entities.enums.MovementType;
import com.reynan.inventorysalesmanagement.exceptions.ResourceNotFoundException;
import com.reynan.inventorysalesmanagement.mapper.ProductMapper;
import com.reynan.inventorysalesmanagement.mapper.StockMovementMapper;
import com.reynan.inventorysalesmanagement.repository.CategoryRepository;
import com.reynan.inventorysalesmanagement.repository.ProductRepository;
import com.reynan.inventorysalesmanagement.repository.StockMovementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductMapper mapperProduct;
    private final StockMovementMapper stockMovementmapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final StockMovementRepository stockMovementRepository;

    public ProductService(ProductMapper mapperProduct, ProductRepository productRepository
    , StockMovementMapper stockMovementmapper, CategoryRepository categoryRepository
    , StockMovementRepository stockMovementRepository) {
        this.mapperProduct = mapperProduct;
        this.productRepository = productRepository;
        this.stockMovementmapper = stockMovementmapper;
        this.categoryRepository = categoryRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    public ProductResponseDTO findById(Long id) {

        logger.debug("Finding product by ID {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        return mapperProduct.toResponseDTO(product);
    }

    @Transactional
    public ProductDetailResponseDTO create(ProductRequestDTO productRequestDTO) {

        logger.debug("Creating new product {}", productRequestDTO.name());

        Product product = mapperProduct.toEntity(productRequestDTO);
        productRepository.save(product);

        logger.info("Product created successfully: {}", product.getId());
        return mapperProduct.toDetailResponseDTO(product);
    }

    @Transactional
    public ProductDetailResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {

        logger.debug("Updating product by ID {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        Category category = categoryRepository.findById(productRequestDTO.categoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + productRequestDTO.categoryId()));

        product.setName(productRequestDTO.name());
        product.setDescription(productRequestDTO.description());
        product.setMinimumStock(productRequestDTO.minimumStock());
        product.setPrice(productRequestDTO.price());
        category.addProduct(product);

        logger.info("Product updated successfully: {}", product.getId());
        productRepository.save(product);

        return mapperProduct.toDetailResponseDTO(product);
    }

    public void addStock(Long productId, StockMovementRequestDTO stockMovementRequestDTO) {

        logger.debug("Adding stock movement by ID {}", productId);

        createStockMoviment(productId, stockMovementRequestDTO.quantity(), MovementType.ENTRY);
    }

    @Transactional
    public void createStockMoviment(Long ProductId, Integer quantity, MovementType movementType) {

        logger.debug("Adding stock movement by ID {}", ProductId);

        Product product = productRepository.findById(ProductId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + ProductId));

        StockMovement stockMovement = new StockMovement(quantity, movementType);

        product.addStockMoviment(stockMovement);

        logger.info("Stock movement created for product ID: {}", product.getId());
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting product by ID {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        productRepository.delete(product);
    }

    public Set<ProductResponseDTO> findAll() {
        logger.debug("Finding all products");

        return mapperProduct.toSetResponseDTO(new HashSet<>(productRepository.findAll()));
    }

    public Page<StockMovementResponseDTO> relatedMovements(Long ProductId, Pageable pageable) {

        logger.debug("Finding product by ID {}", ProductId);

         productRepository.findById(ProductId)
                 .orElseThrow(() -> new ResourceNotFoundException("Product not fount with ID: " + ProductId));

        logger.debug("Finding all stock movement by product");

        Page<StockMovement> page = stockMovementRepository.findByProductId(ProductId, pageable);

        return page.map(stockMovementmapper :: toResponseDTO);
    }
}