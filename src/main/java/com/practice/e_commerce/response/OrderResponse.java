package com.practice.e_commerce.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

  private Long orderId;

  private String orderCode;

  private Long userId;

  private String status;

  private String paymentStatus;

  private BigDecimal subTotal;

  private BigDecimal discountAmount;

  private BigDecimal shippingFee;

  private BigDecimal totalAmount;

  private LocalDateTime createdAt;

  private List<OrderItemResponse> items;
}
