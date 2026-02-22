package com.reynan.inventorysalesmanagement.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CUSTOMER")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "EMAIL")
    private String email;

    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.LAZY
    )
    private List<Sale> sales = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDate createdAt;

    @CreationTimestamp
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    public Customer() {}

    public Customer(String name, String telephone, String email, LocalDate createdAt) {
        this.name = name;
        this.telephone = telephone;
        this.email = email;
        this.createdAt = createdAt;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void addSale(Sale sale) {
        sales.add(sale);
    }

    public void removeSale(Sale sale) {
        sales.remove(sale);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
