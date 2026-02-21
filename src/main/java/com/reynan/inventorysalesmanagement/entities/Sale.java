package com.reynan.inventorysalesmanagement.entities;

import com.reynan.inventorysalesmanagement.entities.enums.SaleStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "SALE")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SALE_DATE")
    private LocalDateTime saleDate;

    @ManyToOne
    @JoinColumn(name = "ID_CUSTOMER")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_SALE")
    private SaleStatus saleStatus;

    @OneToMany(
        mappedBy = "sale",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private List<SaleItem> saleItems = new ArrayList<>();

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    public Sale() {}

    public Sale(LocalDateTime saleDate, Customer customer, SaleStatus saleStatus) {
        this.saleDate = saleDate;
        this.customer = customer;
        this.saleStatus = saleStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(SaleStatus saleStatus) {
        this.saleStatus = saleStatus;
    }

    public List<SaleItem> getSaleItems() {
        return Collections.unmodifiableList(saleItems);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal totalAmount() {

       return saleItems.stream().map(SaleItem :: subTotal)
                .reduce(BigDecimal.ZERO, BigDecimal :: add);
    }

    public void addSaleItem(SaleItem saleItem) {
        saleItems.add(saleItem);
        saleItem.setSale(this);
    }

    public void removeSaleItem(SaleItem saleItem) {
        saleItems.remove(saleItem);
        saleItem.setSale(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(id, sale.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
