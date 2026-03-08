package com.reynan.inventorysalesmanagement.repository;

import com.reynan.inventorysalesmanagement.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
}
