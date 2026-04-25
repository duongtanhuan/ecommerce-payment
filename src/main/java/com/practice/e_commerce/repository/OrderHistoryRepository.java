package com.practice.e_commerce.repository;

import com.practice.e_commerce.entity.OrderHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository
    extends JpaRepository<OrderHistory, Long> {
  List<OrderHistory> findByOrderId(Long id);
}
