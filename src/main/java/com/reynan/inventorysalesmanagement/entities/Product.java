package com.reynan.inventorysalesmanagement.entities;

import com.reynan.inventorysalesmanagement.exceptions.StockException;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.reynan.inventorysalesmanagement.entities.enums.MovementType.ENTRY;

@Entity
@Table(name = "PRODUCT")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPRODUCT")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "QUANTITY_IN_STOCK")
    private Integer quantityInStock;

    @Column(name = "MINIMUM_STOCK")
    private Integer minimumStock;

    @ManyToOne
    @JoinColumn(name = "ID_CATEGORY")
    private Category category;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.PERSIST
    )
    private List<StockMovement> stockMovements = new ArrayList<StockMovement>();

    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    //The update cannot be valid for stock update but rather for StockMoviment
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    public Product() {}

    public Product(Category category,
                   Integer minimumStock, BigDecimal price, String name) {

        this.category = category;
        this.quantityInStock= 0;
        this.minimumStock = minimumStock;
        this.price = price;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public Integer getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(Integer minimumStock) {
        this.minimumStock = minimumStock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public List<StockMovement> getStockMovements() {
        return Collections.unmodifiableList(stockMovements);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void addStockMoviment(StockMovement stockMovement) {
        stockMovements.add(stockMovement);
        stockMovement.setProduct(this);

        if (stockMovement.getMovementType() == ENTRY) {
            increaseStock(stockMovement.getQuantity());
        } else {
            decreaseStock(stockMovement.getQuantity());
        }
    }

    public void increaseStock(int amount) {

        if (amount <= 0) {
            throw new StockException("Amount must be greater than zero");
        }

        this.quantityInStock += amount;
    }

    public void decreaseStock(int amount) {

        if (amount <= 0) {
            throw new StockException("Amount must be greater than zero");
        }

        if (this.quantityInStock - amount < 0) {
            throw new StockException("Insufficient stock");
        }

        this.quantityInStock -= amount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
