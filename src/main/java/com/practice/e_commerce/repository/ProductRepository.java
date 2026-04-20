package com.practice.e_commerce.repository;

import com.practice.e_commerce.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  boolean existsByCode(String code);
  Optional<Product> findByIdAndDeletedFalse(Long id);
}
