package com.practice.e_commerce.controller;

import com.practice.e_commerce.common.OrderStatus;
import com.practice.e_commerce.common.response.PageResponse;
import com.practice.e_commerce.entity.OrderHistory;
import com.practice.e_commerce.repository.OrderHistoryRepository;
import com.practice.e_commerce.request.CreateOrderRequest;
import com.practice.e_commerce.request.UpdateOrderStatusRequest;
import com.practice.e_commerce.response.OrderResponse;
import com.practice.e_commerce.service.OrderService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;
  private final OrderHistoryRepository orderHistoryRepository;

  @PostMapping
  public ResponseEntity<OrderResponse> createOrder(
      @Valid @RequestBody CreateOrderRequest request
  ) {
    OrderResponse response = orderService.createOrder(request, 1L);
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<Void> updateStatus(
      @PathVariable Long id,
      @Valid @RequestBody UpdateOrderStatusRequest request
  ) {
    orderService.updateStatus(id, request.getStatus());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{id}/cancel")
  public ResponseEntity<Void> cancel(@PathVariable Long id) {
    orderService.cancelOrder(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{id}/history")
  public ResponseEntity<List<OrderHistory>> getHistory(@PathVariable Long id) {
    return ResponseEntity.ok(
        orderHistoryRepository.findByOrderId(id)
    );
  }

  @GetMapping
  public ResponseEntity<PageResponse<OrderResponse>> getOrders(
      @RequestParam(required = false) OrderStatus status,
      @RequestParam(required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
      LocalDateTime fromDate,

      @RequestParam(required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
      LocalDateTime toDate,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    return ResponseEntity.ok(orderService.getOrders(status, fromDate, toDate, page, size));
  }
}
