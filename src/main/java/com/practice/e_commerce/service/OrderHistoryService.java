package com.practice.e_commerce.service;

public interface OrderHistoryService {
  void log(Long orderId,
      String action,
      String before,
      String after,
      String note);
}
