package com.practice.e_commerce.repository;

import com.practice.e_commerce.common.OrderStatus;
import com.practice.e_commerce.entity.Order;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query("""
    SELECT o FROM Order o
    WHERE (:status IS NULL OR o.status = :status)
          AND (:fromDate IS NULL OR o.createdAt >= :fromDate)
          AND (:toDate IS NULL OR o.createdAt <= :toDate)
""")
  Page<Order> findAllWithFilter(
      @Param("status") OrderStatus status,
      @Param("fromDate") LocalDateTime fromDate,
      @Param("toDate") LocalDateTime toDate,
      Pageable pageable
  );

}
