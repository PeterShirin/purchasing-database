package com.intellekta.databaseWebApp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SaleRepository extends JpaRepository<Sale, Integer> {
    List<Sale> findByPriceGreaterThan(double price);
}
