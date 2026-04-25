package com.practice.e_commerce.repository;

import com.practice.e_commerce.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
  boolean existsByCode(String code);
  Optional<Product> findByIdAndDeletedFalse(Long id);

  @Modifying
  @Query("""
    UPDATE Product p
    SET p.stock = p.stock - :qty
    WHERE p.id = :id AND p.stock >= :qty
""")
  int decreaseStock(Long id, Integer qty);

  @Modifying
  @Query("""
    UPDATE Product p
    SET p.stock = p.stock + :qty
    WHERE p.id = :id
""")
  void increaseStock(Long id, Integer qty);
}
