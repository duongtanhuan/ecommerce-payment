package com.practice.e_commerce.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class CreateOrderRequest {
  @NotEmpty(message = "order.items.invalid.empty")
  @Valid
  private List<Item> items;

  @Data
  public static class Item {

    @NotNull(message = "order.item.product-id.invalid.null")
    private Long productId;

    @NotNull(message = "order.item.quantity.invalid.null")
    @Min(value = 1, message = "order.item.quantity.invalid.min")
    @Max(value = 1000, message = "order.item.quantity.invalid.max")
    private Integer quantity;
  }
}
