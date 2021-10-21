package com.example.webshop.repositories;

import com.example.webshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    //boolean existsByIdAndAvailable(Long id,boolean isAvailable);
    boolean existsByCode(String code);
}
