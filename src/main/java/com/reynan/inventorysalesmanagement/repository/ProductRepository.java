package com.reynan.inventorysalesmanagement.repository;

import com.reynan.inventorysalesmanagement.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
