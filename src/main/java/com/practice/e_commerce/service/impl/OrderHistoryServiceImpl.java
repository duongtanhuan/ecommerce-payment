package com.practice.e_commerce.service.impl;

import com.practice.e_commerce.entity.OrderHistory;
import com.practice.e_commerce.repository.OrderHistoryRepository;
import com.practice.e_commerce.service.OrderHistoryService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderHistoryServiceImpl implements OrderHistoryService {

  private final OrderHistoryRepository repository;

  public void log(Long orderId,
      String action,
      String before,
      String after,
      String note) {

    OrderHistory history = new OrderHistory();
    history.setOrderId(orderId);
    history.setAction(action);
    history.setStatusBefore(before);
    history.setStatusAfter(after);
    history.setNote(note);
    history.setCreatedAt(LocalDateTime.now());

    repository.save(history);
  }
}
