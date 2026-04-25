package com.practice.e_commerce.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

  private Long productId;

  private String productName;

  private BigDecimal unitPrice;

  private Integer quantity;

  private BigDecimal totalPrice;
}
