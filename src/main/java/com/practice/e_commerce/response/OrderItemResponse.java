package com.practice.e_commerce.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {

  private Long productId;

  private String productName;

  private BigDecimal unitPrice;

  private Integer quantity;

  private BigDecimal totalPrice;
}
