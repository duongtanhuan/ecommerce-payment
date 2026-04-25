package com.practice.e_commerce.dto;

import com.practice.e_commerce.common.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

  private Long id;

  private String orderCode;

  private BigDecimal subTotal;

  private BigDecimal discountAmount;

  private BigDecimal shippingFee;

  private BigDecimal totalAmount;

  private OrderStatus status;

  private LocalDateTime createdAt;

  private List<OrderItemDto> items;
}
