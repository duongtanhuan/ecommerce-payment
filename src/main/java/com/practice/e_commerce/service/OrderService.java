package com.practice.e_commerce.service;

import com.practice.e_commerce.common.OrderStatus;
import com.practice.e_commerce.request.CreateOrderRequest;
import com.practice.e_commerce.response.OrderResponse;
import com.practice.e_commerce.common.response.PageResponse;
import java.time.LocalDateTime;

public interface OrderService {
  OrderResponse createOrder(CreateOrderRequest request, Long userId);
  void updateStatus(Long orderId, OrderStatus newStatus);
  void cancelOrder(Long orderId);
  PageResponse<OrderResponse> getOrders( OrderStatus status, LocalDateTime fromDate, LocalDateTime toDate, int page, int size);
}
