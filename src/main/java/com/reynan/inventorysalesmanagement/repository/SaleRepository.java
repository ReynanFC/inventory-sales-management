package com.reynan.inventorysalesmanagement.repository;

import com.reynan.inventorysalesmanagement.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale,Long> {
}
