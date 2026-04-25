package com.practice.e_commerce.request;

import com.practice.e_commerce.common.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderStatusRequest {
  @NotNull
  private OrderStatus status;
}
