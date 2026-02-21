package com.reynan.inventorysalesmanagement.repository;

import com.reynan.inventorysalesmanagement.entities.*;
import com.reynan.inventorysalesmanagement.entities.enums.MovementType;
import com.reynan.inventorysalesmanagement.entities.enums.SaleStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootTest
@ActiveProfiles("test")
class InventoryIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleItemRepository saleItemRepository;

    @Test
    void setupTestData() {

        // === Clientes ===
        Customer c1 = new Customer("Maria Brown", "988888888", "maria@gmail.com", LocalDateTime.now().toLocalDate());
        Customer c2 = new Customer("Alex Green", "977777777", "alex@gmail.com", LocalDateTime.now().toLocalDate());

        customerRepository.saveAll(Arrays.asList(c1, c2));

        // === Categorias ===
        Category cat1 = new Category("Electronics", "Electronic devices");
        Category cat2 = new Category("Books", "Programming books");

        categoryRepository.saveAll(Arrays.asList(cat1, cat2));

        // === Produtos ===
        Product p1 = new Product(cat1, 5, new BigDecimal("1999.90"), "Smartphone");
        Product p2 = new Product(cat2, 3, new BigDecimal("99.90"), "Java Book");

        productRepository.saveAll(Arrays.asList(p1, p2));

        // Relacionamento categoria -> produto
        cat1.addProduct(p1);
        cat2.addProduct(p2);

        categoryRepository.saveAll(Arrays.asList(cat1, cat2));

        // === Movimentos de estoque (entrada) ===
        StockMovement m1 = new StockMovement(10, MovementType.ENTRY, p1);
        StockMovement m2 = new StockMovement(5, MovementType.ENTRY, p2);

        p1.addStockMoviment(m1);
        p2.addStockMoviment(m2);

        stockMovementRepository.saveAll(Arrays.asList(m1, m2));

        // === Venda ===
        Sale sale = new Sale(LocalDateTime.now(), c1, SaleStatus.COMPLETED);

        // itens da venda
        SaleItem item1 = new SaleItem(sale, p1, 2, p1.getPrice());
        SaleItem item2 = new SaleItem(sale, p2, 1, p2.getPrice());

        sale.addSaleItem(item1);
        sale.addSaleItem(item2);

        saleRepository.save(sale);
        saleItemRepository.saveAll(Arrays.asList(item1, item2));

        // asserts básicos (opcional)
        assert sale.getId() != null;
        assert p1.getQuantityInStock() == 10;
    }
}